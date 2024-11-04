package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.HitClientImpl;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class HitController {
    private final HitClientImpl hitClient;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/stats")
    public List<HitStatDto> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {

        String encodedStart = URLEncoder.encode(start.formatted(formatter), StandardCharsets.UTF_8);
        String encodedEnd = URLEncoder.encode(end.formatted(formatter), StandardCharsets.UTF_8);
        return hitClient.getStats(encodedStart, encodedEnd, uris, unique);
    }

    @PostMapping("/hit")
    public void addHit(@RequestBody HitDto hitDto) {
        hitClient.addHit(hitDto);
    }
}