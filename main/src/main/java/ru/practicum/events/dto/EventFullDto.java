package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.locations.dto.LocationDto;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.users.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class EventFullDto {
    @NotBlank(message = "Annotation cannot be blank.")
    private String annotation;

    @NotNull(message = "Category is required.")
    private CategoryDto category;

    private Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private String description;

    @NotNull(message = "Event date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Integer id;

    @NotNull(message = "Initiator is required.")
    private UserShortDto initiator;

    @NotNull(message = "Location is required.")
    private LocationDto location;

    @NotNull(message = "Paid status is required.")
    private Boolean paid;

    @JsonProperty(defaultValue = "0")
    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    @JsonProperty(defaultValue = "true")
    private Boolean requestModeration;

    private EventState state;

    @NotBlank(message = "Title is required.")
    private String title;

    private Long views;
}
