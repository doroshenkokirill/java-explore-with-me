package ru.practicum.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.events.dto.EventShortDto;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CompilationDto {
    private Set<EventShortDto> events;
    private Integer id;
    private Boolean pinned;
    private String title;
}
