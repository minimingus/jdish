package io.dish.service;

import io.dish.dto.DishDto;
import io.dish.mappers.DishMapper;
import io.dish.model.Dish;
import io.dish.model.Provider;
import io.dish.repository.DishRepository;
import io.dish.repository.ProviderRepository;
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

    @Inject
    ProviderRepository providerRepository;

    public Optional<DishDto> findById(UUID id) {
        return dishRepository.findByDishId(id)
                .map(DishMapper::toDishDto);
    }

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
        Optional<Provider> providerName = providerRepository.findByName(dishDto.getProviderName());

        providerName.map(name -> {
                            System.out.println("provider name " + name + " is present in provider table");
                            System.out.println(dish);
                            dishRepository.persistAndFlush(dish);
                            return dish.getId();
                        }
                )
                .orElseThrow(
                        () -> new RuntimeException("Cannot create dish for a provider which doesnt exist. " +
                                "provider name:" + dishDto.getProviderName()));
    return null;
    }

    @Transactional
    public void deleteDish(DishDto dishDto) {
        dishRepository.deleteDish(dishDto);

    }


}
