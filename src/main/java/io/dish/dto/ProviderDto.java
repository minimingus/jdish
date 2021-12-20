package io.dish.dto;

import java.util.Set;

public class ProviderDto {

    private String name;
    private Set<DishDto> dishes;

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
