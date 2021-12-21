package io.dish.dto;

import lombok.Value;

import java.util.Set;

@Value
public class ProviderDto {

    String name;
    Set<DishDto> dishes;

}
