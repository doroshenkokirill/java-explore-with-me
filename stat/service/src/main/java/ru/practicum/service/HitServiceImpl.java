package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;
import ru.practicum.model.Hit;
import ru.practicum.model.HitMapper;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private static final Logger log = LoggerFactory.getLogger(HitServiceImpl.class);
    private final HitRepository hitRepository;

    @Override
    public void addHit(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);
        hitRepository.save(hit);
        log.info("Added hit: {}", hit);
    }

    @Override
    public List<HitStatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Start to getStats({}, {}, {})", uris, start, end);
        log.info("Base Hit: {}", hitRepository.findAll());
        List<HitStatDto> result;
        if (uris == null || uris.isEmpty()) {
            return new ArrayList<>();
        }
        if (unique) {
            log.info("Retrieving unique hits");
            result = hitRepository.findUniqueHits(start, end, uris);
        } else {
            log.info("Retrieving all hits");
            result = hitRepository.findAllHits(start, end, uris);
        }
        return result;
    }
}