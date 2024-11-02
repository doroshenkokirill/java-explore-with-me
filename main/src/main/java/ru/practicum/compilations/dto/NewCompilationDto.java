package ru.practicum.compilations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class NewCompilationDto {
    private Set<Integer> events;

    @JsonProperty(defaultValue = "false")
    @Builder.Default
    private Boolean pinned = false;

    @NotBlank(message = "Field title must not be blank")
    @Size(min = 1, max = 50, message = "Size of field title must be [1;50]")
    private String title;
}
