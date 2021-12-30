package io.dish.events;

import io.dish.dto.DishDto;
import io.dish.dto.DishUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;


@EqualsAndHashCode(callSuper = true)
@Value
public class DishUpdatedEvent extends BaseEvent{
    DishDto original;
    DishUpdateDto updateDto;

    @Builder

    public DishUpdatedEvent(EventType type, Instant timestamp, DishDto original, DishUpdateDto updateDto) {
        super(type, timestamp);
        this.original = original;
        this.updateDto = updateDto;
    }
}
