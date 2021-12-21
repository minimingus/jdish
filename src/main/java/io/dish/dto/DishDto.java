package io.dish.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class DishDto {

    String name;
    String description;
    UUID providerId;

}
