package service.src.main.java.ru.practicum.controller;

import dto.src.main.java.ru.practicum.dto.HitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.src.main.java.ru.practicum.service.HitService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class HitController {
    private final HitService hitServiceImpl;

    @PostMapping("/hit")
    public void addHit(@RequestBody HitDto hitDto) {
        hitServiceImpl.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<HitStatDto> getStats(@RequestParam String start,
                                     @RequestParam String end,
                                     @RequestParam(required = false) List<String> uris,
                                     @RequestParam(defaultValue = "false") Boolean unique) {
        return hitServiceImpl.getStats(start, end, uris, unique);
    }
}