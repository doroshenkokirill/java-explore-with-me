package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;
import ru.practicum.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class HitsController {
    private final HitService hitServiceImpl;
    private static final String formatForString = "yyyy-MM-dd HH:mm:ss";

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody HitDto hitDto) {
        hitServiceImpl.addHit(hitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<HitStatDto> getStats(@DateTimeFormat(pattern = formatForString) LocalDateTime start,
                                     @DateTimeFormat(pattern = formatForString) LocalDateTime end,
                                     @RequestParam(required = false) List<String> uris,
                                     @RequestParam(required = false) Boolean unique) {
        return hitServiceImpl.getStats(start, end, uris, unique);
    }
}