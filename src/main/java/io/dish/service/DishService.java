package io.dish.service;

import io.dish.dto.DishDto;
import io.dish.mappers.DishMapper;
import io.dish.model.Dish;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DishService {

    @Inject
    DishRepository dishRepository;

    public Optional<DishDto> findByName(String dishName) {
        return dishRepository.findByName(dishName)
                .map(DishMapper::toDishDto);
    }


    @Transactional
    public UUID createDish (DishDto dishDto) {
        System.out.println("creating dish");
        Dish dish = DishMapper.toDish(dishDto);
        System.out.println(dish);
        dishRepository.persistAndFlush(dish);
        return dish.getId();

    }



}
