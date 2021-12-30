package io.dish.dto;

import java.util.HashSet;
import java.util.Set;

public class ProviderDto {

    String name;
    Set<DishDto> dishes = new HashSet<>();

    public ProviderDto() {}

    public ProviderDto(String name, Set<DishDto> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public Set<DishDto> getDishes() {
        return dishes;
    }
}
