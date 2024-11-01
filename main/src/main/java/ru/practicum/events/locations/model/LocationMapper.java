package ru.practicum.events.locations.model;

import lombok.experimental.UtilityClass;
import ru.practicum.events.locations.dto.LocationDto;

@UtilityClass
public class LocationMapper {
    public Location toLocation(LocationDto locationDto) {
        return Location.builder().lat(locationDto.getLat()).lon(locationDto.getLon()).build();
    }

    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder().lat(location.getLat()).lon(location.getLon()).build();
    }
}
