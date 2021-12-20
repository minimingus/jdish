package io.dish.dto;

import java.util.UUID;

public class DishDto {

    private String name;
    private String description;
    private UUID providerId;

    public DishDto() {}

    public DishDto(String name, String description, UUID providerId) {
        this.name = name;
        this.description = description;
        this.providerId = providerId;
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
        return "DishDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", providerId=" + providerId +
                '}';
    }

}
