package ru.practicum.events.model;

import lombok.experimental.UtilityClass;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.locations.model.LocationMapper;
import ru.practicum.users.model.UserMapper;

@UtilityClass
public class EventMapper {
    public EventShortDto toEventShortDto(Event e) {
        return EventShortDto.builder()
                .annotation(e.getAnnotation())
                .category(CategoryMapper.toCategoryDto(e.getCategory()))
                .confirmedRequests(e.getConfirmedRequests())
                .eventDate(e.getEventDate())
                .id(e.getId())
                .initiator(UserMapper.toUserShortDto(e.getInitiator()))
                .paid(e.getPaid())
                .title(e.getTitle())
                .views(e.getViews())
                .build();
    }

    public EventFullDto toEventFullDto(Event e) {
        return EventFullDto.builder()
                .annotation(e.getAnnotation())
                .category(CategoryMapper.toCategoryDto(e.getCategory()))
                .confirmedRequests(e.getConfirmedRequests())
                .createdOn(e.getCreatedOn())
                .description(e.getDescription())
                .eventDate(e.getEventDate())
                .id(e.getId())
                .initiator(UserMapper.toUserShortDto(e.getInitiator()))
                .location(LocationMapper.toLocationDto(e.getLocation()))
                .paid(e.getPaid())
                .participantLimit(e.getParticipantLimit())
                .publishedOn(e.getPublishedOn())
                .requestModeration(e.getRequestModeration())
                .state(e.getState())
                .title(e.getTitle())
                .views(e.getViews())
                .build();
    }
}
