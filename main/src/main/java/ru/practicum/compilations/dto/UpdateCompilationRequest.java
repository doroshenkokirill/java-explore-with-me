package ru.practicum.compilations.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class UpdateCompilationRequest {
    private Set<Integer> events;

    private int id;

    private Boolean pinned;

    @Size(min = 1, max = 50, message = "Size of field title must be [1;50]")
    private String title;
}
