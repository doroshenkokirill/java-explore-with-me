package ru.practicum.compilations.model;

import lombok.experimental.UtilityClass;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.EventMapper;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public CompilationDto toCompilationDto(Compilation compilation) {

        Set<EventShortDto> eventShortDtoSet = compilation.getEvents().stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toSet());

        return CompilationDto.builder()
                .id(compilation.getId())
                .events(eventShortDtoSet)
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }
}
