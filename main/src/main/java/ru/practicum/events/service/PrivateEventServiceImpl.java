package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.events.dto.*;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getAll(int userId, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto create(int userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto getByEventId(int userId, int eventId) {
        return null;
    }

    @Override
    public EventFullDto update(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId) {
        return List.of();
    }

    @Override
    public EventRequestStatusUpdateResult updateStatus(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }
}
