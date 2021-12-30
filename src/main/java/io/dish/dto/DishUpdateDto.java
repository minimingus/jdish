package io.dish.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DishUpdateDto {
    String description;

    public DishUpdateDto() {}
}
