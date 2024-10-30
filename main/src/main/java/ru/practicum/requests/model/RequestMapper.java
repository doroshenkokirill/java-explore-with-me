package ru.practicum.requests.model;

import lombok.experimental.UtilityClass;
import ru.practicum.requests.dto.ParticipationRequestDto;

@UtilityClass
public class RequestMapper {
    public ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .requester(request.getRequester().getId())
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .status(String.valueOf(request.getStatus()))
                .build();
    }
}