package ru.practicum.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(int compId) {

    }

    @Override
    public CompilationDto updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
