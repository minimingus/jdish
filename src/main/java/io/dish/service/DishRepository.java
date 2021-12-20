package io.dish.service;

import io.dish.model.Dish;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;


@ApplicationScoped
public class DishRepository implements PanacheRepository<Dish> {

    public Optional<Dish> findByName(String name) {
        return Optional.ofNullable(find("name", name).firstResult());

    }
}
