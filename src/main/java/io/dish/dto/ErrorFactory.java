package io.dish.dto;

public class ErrorFactory {

    public static ErrorDto ofNotFound(String resource) {
        return new ErrorDto(404, String.format("Resource [%s] was not found",resource));
    }


    public static ErrorDto couldNotCreate(String resource, String reason) {
        return new ErrorDto(404, String.format("Resource [%s] could not be created due to [%s] ",resource, reason));
    }
}
