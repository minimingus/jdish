package io.dish.repository;

import io.dish.model.Dish;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.vavr.control.Option;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@ApplicationScoped
public class DishRepository implements PanacheRepository<Dish> {

    public Optional<Dish> findByName(String name) {
        return Optional.ofNullable(find("name", name).firstResult());
    }

    public Option<List<Dish>> findAllDishes() {
        return Option.of(findAll().list());

    }
}
