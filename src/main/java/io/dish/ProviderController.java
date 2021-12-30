package io.dish;

import io.dish.dto.ErrorFactory;
import io.dish.service.ProviderService;
import io.dish.dto.ProviderDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/providers")
public class ProviderController {

    @Inject
    ProviderService providerService;

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProviderByName(@PathParam("name") String name) {
        return providerService.findByName(name)
                .map(d -> Response.status(Response.Status.ACCEPTED).entity(d).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).entity(ErrorFactory.ofNotFound("provider")).build());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProvider(ProviderDto providerDto) {
//        try {
            UUID providerId = providerService.createProvider(providerDto);
            System.out.println("provider id: " + providerId);
            return Response.status(Response.Status.CREATED).entity(providerId).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity(e.getCause().getMessage()).build();
//        }
    }
}
