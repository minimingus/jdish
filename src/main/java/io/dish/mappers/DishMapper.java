package io.dish.mappers;

import io.dish.dto.DishDto;
import io.dish.model.Dish;

public class DishMapper {

    public static DishDto toDishDto (Dish dish) {
        return new DishDto(dish.getName(), dish.getDescription(), dish.getProviderName());
    }

    public static Dish toDish (DishDto dishDto) {
        return new Dish(dishDto.getName(), dishDto.getDescription(), dishDto.getProviderName());
    }
}
