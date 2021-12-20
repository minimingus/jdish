package io.dish.mappers;

import io.dish.dto.ProviderDto;
import io.dish.model.Dish;
import io.dish.model.Provider;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProviderMapper {

    public static ProviderDto toProviderDto (Provider provider) {
        return new ProviderDto(provider.getName(), provider.getDishes().stream().map(DishMapper::toDishDto).collect(Collectors.toSet()));
    }

    public static Provider toProvider (ProviderDto providerDto) {
        Optional<Set<Dish>> dishes = Optional.ofNullable(providerDto.getDishes()).map(d -> d.stream().map(DishMapper::toDish).collect(Collectors.toSet()));
        dishes.stream().peek(System.out::println);
        return new Provider(providerDto.getName(), dishes.orElse(Set.of()));
    }
}
