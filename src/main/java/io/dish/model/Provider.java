package io.dish.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
public class Provider {
    @Id
    @GeneratedValue
    UUID id;

    @Column(unique=true)
    String name;

    @OneToMany(mappedBy="providerId")
    @ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Provider provider = (Provider) o;
        return id != null && Objects.equals(id, provider.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
