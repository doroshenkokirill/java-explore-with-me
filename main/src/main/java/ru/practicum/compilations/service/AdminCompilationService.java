package ru.practicum.compilations.service;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(int compId);

    CompilationDto updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest);
}
