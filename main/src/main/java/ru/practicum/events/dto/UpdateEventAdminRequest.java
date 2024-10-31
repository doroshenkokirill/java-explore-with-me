package ru.practicum.events.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.events.model.enums.AdminEventStateAction;

@Data
@Builder
@AllArgsConstructor
public class UpdateEventAdminRequest {
    private AdminEventStateAction stateAction;
}
