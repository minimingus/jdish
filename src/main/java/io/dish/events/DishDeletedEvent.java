package io.dish.events;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;


@Value
public class DishDeletedEvent {
    EventType type;
    String providerName;
    String dishName;
    Instant deletedAt;
}
