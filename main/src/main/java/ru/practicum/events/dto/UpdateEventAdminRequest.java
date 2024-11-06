package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.events.model.enums.AdminEventStateAction;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UpdateEventAdminRequest extends UpdateEventRequest {
    private AdminEventStateAction stateAction;
}
