package ru.practicum.compilations.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.service.AdminCompilationService;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return adminCompilationService.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int compId) {
        adminCompilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable int compId,
                                 @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        return adminCompilationService.updateCompilation(compId, updateCompilationRequest);
    }
}
