package ru.practicum.events.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.HitClientImpl;
import ru.practicum.dto.HitDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final PublicEventService eventService;
    private final HitClientImpl hitClient;

    @GetMapping
    public List<EventShortDto> getEventList(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) Set<Integer> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") @Positive int size,
                                            HttpServletRequest request) {
        List<EventShortDto> filteredEvents = eventService.getEventsList(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);

        HitDto hit = HitDto.builder()
                .timestamp(LocalDateTime.now())
                .app("main-ewm")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr()).build();
        hitClient.addHit(hit);
        return filteredEvents;
    }

    @GetMapping("/{id}")
    public EventFullDto get(@PathVariable int id) {
        return eventService.get(id);
    }
}
