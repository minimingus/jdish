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
@Table(uniqueConstraints = { @UniqueConstraint(name="providerExistsByName", columnNames = { "name"}) })

public class Provider {
    @Id
    @GeneratedValue
    UUID providerId;

    String name;

    @OneToMany(mappedBy = "providerName")
    @ToString.Exclude
    Set<Dish> dishes;

    public Provider(String name, Set<Dish> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public Provider() {
    }

    public UUID getProviderId() {
        return providerId;
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
        return providerId != null && Objects.equals(providerId, provider.providerId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
