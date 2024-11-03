package ru.practicum.events.service;

import ru.practicum.client.HitClientImpl;
import ru.practicum.dto.HitStatDto;
import ru.practicum.events.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommonEventService {

    public static Long getViews(Event event, HitClientImpl hitClient) {
        String uris = "/events/" + event.getId();
        LocalDateTime start = event.getPublishedOn() != null ? event.getPublishedOn() : event.getCreatedOn();
        LocalDateTime end = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startFormatted = start.format(formatter);
        String endFormatted = end.format(formatter);

        List<HitStatDto> hitStatDtoList = hitClient.getStats(startFormatted, endFormatted, List.of(uris), true);
        return hitStatDtoList.stream().findFirst().map(HitStatDto::getHits).orElse(0L);
    }
}
