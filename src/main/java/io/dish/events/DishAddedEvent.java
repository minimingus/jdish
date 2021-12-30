package io.dish.events;

import lombok.Value;

import java.time.Instant;


@Value
public class DishAddedEvent {
    EventType type;
    String providerName;
    String dishName;
    Instant createdAt;
}
