package ru.practicum.events.service;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventFullDto> getEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEvent(int eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
