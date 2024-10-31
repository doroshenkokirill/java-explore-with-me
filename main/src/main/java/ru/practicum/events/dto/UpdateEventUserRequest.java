package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.events.model.enums.UserEventStateAction;

@Data
@Builder
@AllArgsConstructor
public class UpdateEventUserRequest {
    private UserEventStateAction stateAction;
}
