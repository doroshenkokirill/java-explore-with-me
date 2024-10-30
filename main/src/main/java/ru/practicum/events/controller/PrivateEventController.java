package ru.practicum.events.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.*;
import ru.practicum.events.service.PrivateEventService;
import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final PrivateEventService eventService;

    @GetMapping
    public List<EventShortDto> getAll(@PathVariable int userId,
                                      @RequestParam(defaultValue = "0") int from,
                                      @RequestParam(defaultValue = "10") int size) {
        return eventService.getAll(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable int userId,
                               @RequestBody @Valid NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getByEventId(@PathVariable int userId,
                                     @PathVariable int eventId) {
        return eventService.getByEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable int userId,
                               @PathVariable int eventId,
                               @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return eventService.update(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable int userId, @PathVariable int eventId) {
        return eventService.getRequestsByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateStatus(@PathVariable int userId,
                                                       @PathVariable int eventId,
                                                       @RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return eventService.updateStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
