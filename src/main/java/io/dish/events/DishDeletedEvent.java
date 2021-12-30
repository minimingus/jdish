package io.dish.events;

import lombok.*;

import java.time.Instant;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)

@Value
public class DishDeletedEvent extends BaseEvent {
    String providerName;
    String dishName;

    @Builder
    public DishDeletedEvent(EventType type, Instant timestamp, String providerName, String dishName) {
        super(type, timestamp);
        this.providerName = providerName;
        this.dishName = dishName;
    }
}
