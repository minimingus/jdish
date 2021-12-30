package io.dish.events;

import lombok.*;

import java.time.Instant;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class BaseEvent {
    EventType type;
    Instant timestamp;

}
