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

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody HitDto hitDto) {
        hitServiceImpl.addHit(hitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<HitStatDto> getStats(@RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                     @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                     @RequestParam(value = "uris", required = false) List<String> uris,
                                     @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        return hitServiceImpl.getStats(start, end, uris, unique);
    }
}