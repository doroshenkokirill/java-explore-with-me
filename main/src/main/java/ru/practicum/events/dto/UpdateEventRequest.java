package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.locations.dto.LocationDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateEventRequest {
    @Size(min = 20, max = 2000, message = "Length annotation should be [20;2000]")
    private String annotation;

    private Integer category;

    @Size(min = 20, max = 7000, message = "Length description should be [20;7000]")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private LocationDto location;
    private Boolean paid;

    @PositiveOrZero(message = "Participants limit should not be negative.")
    private Integer participantLimit;

    private Boolean requestModeration;

    @Size(min = 3, max = 120, message = "Length title should be [3;120]")
    private String title;
}
