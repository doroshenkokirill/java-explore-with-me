package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.HitClientImpl;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class HitController {
    private final HitClientImpl hitClient;

    @GetMapping("/stats")
    public List<HitStatDto> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        return hitClient.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public void addHit(@RequestBody HitDto hitDto) {
        hitClient.addHit(hitDto);
    }
}