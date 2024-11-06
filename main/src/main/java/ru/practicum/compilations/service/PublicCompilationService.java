package ru.practicum.compilations.service;

import ru.practicum.compilations.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(int compId);
}
