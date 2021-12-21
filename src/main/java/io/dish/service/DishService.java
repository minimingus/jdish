package io.dish.service;

import io.dish.dto.DishDto;
import io.dish.mappers.DishMapper;
import io.dish.model.Dish;
import io.dish.repository.DishRepository;
import io.vavr.control.Option;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class DishService {

    @Inject
    DishRepository dishRepository;

    public Optional<DishDto> findByName(String dishName) {
        return dishRepository.findByName(dishName)
                .map(DishMapper::toDishDto);
    }

    public Option<List<DishDto>> findAllDishes() {
        return dishRepository.findAllDishes()
                .map(dishes -> dishes.stream()
                        .map(DishMapper::toDishDto)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public UUID createDish(DishDto dishDto) {
        System.out.println("creating dish");
        Dish dish = DishMapper.toDish(dishDto);
        System.out.println(dish);
        dishRepository.persistAndFlush(dish);
        return dish.getId();
    }


}
