package ru.practicum.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.repository.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        return List.of();
    }

    @Override
    public CompilationDto getCompilationById(int compId) {
        return null;
    }
}
