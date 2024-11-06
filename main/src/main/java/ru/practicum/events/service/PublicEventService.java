package ru.practicum.events.service;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PublicEventService {
    List<EventShortDto> getEventsList(String text, Set<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                      String sort, int from, int size);

    EventFullDto get(int id);
}
