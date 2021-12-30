package io.dish.kafka;

import io.dish.dto.DishDto;
import io.dish.events.DishAddedEvent;
import io.dish.events.DishDeletedEvent;
import io.dish.events.DishUpdatedEvent;
import io.dish.events.EventType;
import io.dish.dto.DishUpdateDto;
import io.smallrye.reactive.messaging.kafka.Record;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;

@ApplicationScoped
public class DishEventProducer {

    @Inject
    @Channel("dishes-out")
    Emitter<Record<String, JsonObject>> emitter;

    public void sendDishCreatedEvent(DishDto dish) {
        String key = generateDishKey(dish);
        DishAddedEvent dishAddedEvent = DishAddedEvent.builder()
                .type(EventType.ADD)
                .providerName(dish.getProviderName())
                .dishName(dish.getName())
                .timestamp(Instant.now())
                .build();
        emitter.send(Record.of(key, JsonObject.mapFrom(dishAddedEvent)));
    }

    public void sendDishUpdatedEvent(DishDto original, DishUpdateDto dishUpdateDto) {
        String key = generateDishKey(original);
        DishUpdatedEvent dishUpdatedEvent = DishUpdatedEvent.builder()
                .type(EventType.UPDATE)
                .original(original)
                .updateDto(dishUpdateDto)
                .timestamp(Instant.now())
                .build();
        emitter.send(Record.of(key, JsonObject.mapFrom(dishUpdatedEvent)));
    }


    public void sendDishDeletedEvent(DishDto dishDto) {
        String key = generateDishKey(dishDto);
        DishDeletedEvent dishDeletedEvent = DishDeletedEvent.builder()
                .type(EventType.DELETE)
                .providerName(dishDto.getProviderName())
                .dishName(dishDto.getName())
                .timestamp(Instant.now())
                .build();
        emitter.send(Record.of(key, JsonObject.mapFrom(dishDeletedEvent)));
    }

    private String generateDishKey(DishDto dishDto) {
        return dishDto.getProviderName() + "^" + dishDto.getName();
    }
}
