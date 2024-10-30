package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto updateEvent(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
