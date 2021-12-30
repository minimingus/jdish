package io.dish.controller;

import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.isIn;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException e) {
        if (e instanceof PersistenceException) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cons = (ConstraintViolationException) e.getCause();
                if (cons.getConstraintName().equals("providerexistsbyname")) {
                    return Response.status(400).entity("Provider already exists").build();
                } else {
                    Response.status(400).entity(e.getMessage()).build();
                }
            }
        }
        return Response.status(500).build();
    }
}
