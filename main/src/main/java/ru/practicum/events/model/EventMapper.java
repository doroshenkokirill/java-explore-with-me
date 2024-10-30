package ru.practicum.events.model;

import lombok.experimental.UtilityClass;
import ru.practicum.events.dto.EventShortDto;

@UtilityClass
public class EventMapper {
    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder().build();
    }
}
