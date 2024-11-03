package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;
import ru.practicum.exceptions.DataTimeException;
import ru.practicum.model.Hit;
import ru.practicum.model.HitMapper;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public void addHit(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);
        hitRepository.save(hit);
        log.info("All hits: {}", hitRepository.findAll());
    }

    @Override
    public List<HitStatDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        log.info("Start to getStats({}, {}, {}, {})", uris, start, end, unique);
        if (end.isBefore(start)) {
            throw new DataTimeException("Начало позже конца. Ошибка");
        }
        List<HitStatDto> result;
        if (unique) {
            if (uris == null || uris.isEmpty()) {
                result = hitRepository.findAllUniqueHitsWhenUriIsEmpty(start, end);
            } else {
                result = hitRepository.findAllUniqueHitsWhenUriIsNotEmpty(start, end, uris);
            }
        } else {
            if (uris == null || uris.isEmpty()) {
                result = hitRepository.findAllHitsWhenUriIsEmpty(start, end);
            } else {
                result = hitRepository.findAllHitsWhenStarEndUris(start, end, uris);
            }
        }
        log.info("End to getStats {}", result);
        return result;
    }
}