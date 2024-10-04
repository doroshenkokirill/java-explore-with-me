package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;


import java.util.List;

public interface HitService {
    void addHit(HitDto hitDto);

    List<HitStatDto> getStats(String start, String end, List<String> uris, Boolean unique);
}