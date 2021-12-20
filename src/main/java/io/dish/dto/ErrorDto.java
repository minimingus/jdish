package io.dish.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorDto {
    private long code;
    private String reason;

    public ErrorDto(long code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public ErrorDto() {}

}
