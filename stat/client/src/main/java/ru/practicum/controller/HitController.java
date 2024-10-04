package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.HitClient;
import ru.practicum.dto.HitDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class HitController {
    private final HitClient hitClient;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        return hitClient.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestBody HitDto hitDto) {
        return hitClient.addHit(hitDto);
    }
}