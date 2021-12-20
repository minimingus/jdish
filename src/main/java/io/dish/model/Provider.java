package io.dish.model;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class Provider {
    @Id
    @GeneratedValue
    UUID id;

    @Column(unique=true)
    String name;

    @OneToMany(mappedBy="providerId")
    Set<Dish> dishes;

    public Provider(String name, Set<Dish> dishes) {
        this.name = name;
        this.dishes = dishes;
    }
    public Provider() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }
}
