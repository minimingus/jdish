package io.dish.events;

import lombok.Value;

import java.time.Instant;


@Value
public class DishAddedEvent {
    String name;
    Instant createdAt;
}
