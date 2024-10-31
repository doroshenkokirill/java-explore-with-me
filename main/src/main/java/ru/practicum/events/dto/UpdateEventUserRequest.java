package ru.practicum.events.dto;

import lombok.*;
import ru.practicum.events.model.enums.UserEventStateAction;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UpdateEventUserRequest extends UpdateEventRequest {
    private UserEventStateAction stateAction;
}
