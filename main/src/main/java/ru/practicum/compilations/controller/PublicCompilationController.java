package ru.practicum.compilations.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.service.PublicCompilationService;

import java.util.List;


@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> get(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                    @RequestParam(value = "from", defaultValue = "0") int from,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return publicCompilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable int compId) {
        return publicCompilationService.getCompilationById(compId);
    }
}
