package io.dish.service;

import io.dish.dto.DishDto;
import io.dish.mappers.DishMapper;
import io.dish.model.Dish;
import io.dish.dto.DishUpdateDto;
import io.dish.repository.DishRepository;
import io.dish.repository.ProviderRepository;
import io.vavr.control.Option;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        System.out.println("service - dish - create");
        Dish dish = DishMapper.toDish(dishDto);
        return providerRepository.findByName(dishDto.getProviderName())
                .map(name -> {
                            dishRepository.persistAndFlush(dish);
                            return dish.getId();
                        })
                .orElseThrow(() -> new RuntimeException("missing provider:" + dishDto.getProviderName()));
    }

    @Transactional
    public void updateDish(DishUpdateDto dishUpdateDto) {
        System.out.println("service - dish - update");
        dishRepository.updateDish(dishUpdateDto);
    }

    @Transactional
    public void deleteDish(DishDto dishDto) {
        System.out.println("service - dish - delete");
        dishRepository.deleteDish(dishDto);

    }


}
