package io.dish;

import io.dish.dto.DishDto;
import io.dish.dto.ErrorFactory;
import io.dish.service.DishService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/dishes")
public class DishController {

    @Inject
    DishService dishService;

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishByName(@PathParam("name") String name) {
        return dishService.findByName(name)
                .map(d -> Response.status(Response.Status.ACCEPTED).entity(d).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(ErrorFactory.ofNotFound("dish")).build());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDish(DishDto dishDto) {
        try {
            UUID dishId = dishService.createDish(dishDto);
            System.out.println("dish id: " + dishId);
            return Response.status(Response.Status.CREATED).entity(dishId).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
