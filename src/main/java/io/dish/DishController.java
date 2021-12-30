package io.dish;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dish.dto.DishDto;
import io.dish.dto.ErrorFactory;
import io.dish.json.Schemas;
import io.dish.kafka.DishEventProducer;
import io.dish.service.DishService;
import io.vavr.control.Try;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.UUID;

import static io.vavr.Predicates.instanceOf;
import static io.vavr.API.*;
import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path("/dishes")
public class DishController {

    @Inject
    DishService dishService;

    @Inject
    DishEventProducer dishEventProducer;

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishByName(@PathParam("name") String name) {
        return dishService.findByName(name)
                .map(d -> Response.status(ACCEPTED).entity(d).build())
                .orElse(Response.status(NOT_FOUND).entity(ErrorFactory.ofNotFound("dish")).build());
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishes() {
        return dishService.findAllDishes()
                .map(d -> Response.status(OK).entity(d).build())
                .getOrElse(Response.status(NOT_FOUND).entity(ErrorFactory.ofNotFound("dish")).build());
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDish(@PathParam("id") UUID dishId) {
        try {
            return dishService.findById(dishId).map(dish -> {
                dishService.deleteDish(dish);
                dishEventProducer.sendDishDeletedEvent(dish.getProviderName(), dish.getName());
                return Response.accepted().entity("dish id " + dishId + " was deleted").build();
            }).orElseGet(() -> Response.status(NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response createDish(DishDto dishDto) {
        try {
            validateSchema(dishDto, Schemas.DISH_CREATE);

            UUID dishId = dishService.createDish(dishDto);
            dishEventProducer.sendDishCreatedEvent(dishDto);
            System.out.println( "dish id is:"  + dishId);
            return Response.status(CREATED).entity(dishId).build();
        } catch (Exception e) {
            String cause = Match(e).of(
                    Case($(instanceOf(ValidationException.class)),
                            validationEx ->
                                    validationEx.getCausingExceptions() != null ?
                                            validationEx.getAllMessages().stream()
                                                    .reduce("", (s, a) -> s.concat(a + "\r\n"))
                                            : validationEx.getErrorMessage()
                    ),
                    Case($(instanceOf(ConstraintViolationException.class)),
                            con ->
                                con.getConstraintName().equals("uniqueKey1") ?
                                       "Dish already exist for the combination of provider name and dish name" + con.getMessage()
                                        : con.getConstraintName()
                    ),
                    Case($(), e.getMessage()));

            return Response.status(BAD_REQUEST).entity(ErrorFactory.couldNotCreate("dish", cause)).build();
        }
    }

    private void validateSchema(DishDto dishDto, String schemaName) {
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(schemaName))));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = Try.of(() -> objectMapper.writeValueAsString(dishDto)).getOrElseThrow(e -> new RuntimeException(e.getMessage()));
        JSONObject jsonObject = new JSONObject(new JSONTokener(json));
        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonObject);
    }
}
