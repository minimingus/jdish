package io.dish.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "providerId" }) })
public class Dish {
    @Id @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private UUID providerId;

    public Dish(String name, String description, UUID providerId) {
        this.name = name;
        this.description = description;
        this.providerId = providerId;
    }

    public Dish() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getProviderId() {
        return providerId;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", providerId=" + providerId +
                '}';
    }
}
