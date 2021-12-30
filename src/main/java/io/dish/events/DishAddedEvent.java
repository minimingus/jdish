package io.dish.events;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;


@Value
public class DishAddedEvent extends BaseEvent{
    String providerName;
    String dishName;

    @Builder
    public DishAddedEvent(EventType type, Instant timestamp, String providerName, String dishName) {
        super(type, timestamp);
        this.providerName = providerName;
        this.dishName = dishName;
    }
}
