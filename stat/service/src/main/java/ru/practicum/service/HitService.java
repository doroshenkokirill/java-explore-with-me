package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface HitService {
    void addHit(HitDto hitDto);

    List<HitStatDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique);
}