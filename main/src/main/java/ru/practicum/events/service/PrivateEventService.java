package ru.practicum.events.service;

import ru.practicum.events.dto.*;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {
    List<EventShortDto> getAll(int userId, int from, int size);

    EventFullDto create(int userId, NewEventDto newEventDto);

    EventFullDto getByEventId(int userId, int eventId);

    EventFullDto update(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId);

    EventRequestStatusUpdateResult updateStatus(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
