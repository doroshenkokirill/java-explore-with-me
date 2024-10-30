package ru.practicum.locations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LocationDto {
    private int lat;
    private int lon;
}
