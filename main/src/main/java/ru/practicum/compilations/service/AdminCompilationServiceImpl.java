package ru.practicum.compilations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.compilations.model.CompilationMapper;
import ru.practicum.compilations.repository.CompilationRepository;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.NotFoundException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        List<Event> eventsList = Optional.ofNullable(newCompilationDto.getEvents())
                .filter(eventIds -> !eventIds.isEmpty())
                .map(eventRepository::findAllById)
                .orElse(Collections.emptyList());

        Compilation compilation = Compilation.builder()
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .events(new HashSet<>(eventsList))
                .build();

        Compilation savedCompilation = compilationRepository.save(compilation);
        CompilationDto result = CompilationMapper.toCompilationDto(savedCompilation);
        log.info("Created new compilation: {}", result);
        return result;
    }

    @Override
    public void deleteCompilation(int compId) {
        checkId(compId);
        log.info("Deleting compilation: {}", compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(int compId, UpdateCompilationRequest updateCompilationRequest) {
        checkId(compId);
        log.info("Start to updating compilation: {}", compId);
        Compilation compilation = compilationRepository.findById(compId).get();

        Optional.ofNullable(updateCompilationRequest.getPinned())
                .ifPresent(compilation::setPinned);
        Optional.ofNullable(updateCompilationRequest.getTitle())
                .ifPresent(compilation::setTitle);
        Optional.ofNullable(updateCompilationRequest.getEvents())
                .map(eventRepository::findAllById)
                .map(HashSet::new)
                .ifPresent(compilation::setEvents);

        CompilationDto result = CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
        log.info("Updated compilation: {}", result);
        return result;
    }

    private void checkId(int compId) {
        if (!compilationRepository.existsById(compId)) {
            log.info("Compilation id {} dont exists", compId);
            throw new NotFoundException("Not found compilation with id: " + compId);
        }
    }
}
