package io.dish.kafka;

import io.dish.dto.DishDto;
import io.dish.events.DishAddedEvent;
import io.dish.events.DishDeletedEvent;
import io.dish.events.EventType;
import io.smallrye.reactive.messaging.kafka.Record;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class DishEventProducer {

    @Inject
    @Channel("dishes-out")
    Emitter<Record<String, JsonObject>> emitter;

    public void sendDishCreatedEvent(DishDto dish) {
        String key = dish.getProviderName() + "^" + dish.getName();
        DishAddedEvent dishAddedEvent = new DishAddedEvent(EventType.ADDED, dish.getProviderName(), dish.getName(), Instant.now());
        emitter.send(Record.of(key, JsonObject.mapFrom(dishAddedEvent)));
    }


    public void sendDishDeletedEvent(String providerName, String dishName) {
        String key = providerName + "^" + dishName;
        DishDeletedEvent dishDeletedEvent = new DishDeletedEvent(EventType.DELETED, providerName, dishName, Instant.now());
        emitter.send(Record.of(key, JsonObject.mapFrom(dishDeletedEvent)));
    }
}
