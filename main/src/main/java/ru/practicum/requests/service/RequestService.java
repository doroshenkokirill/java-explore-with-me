package ru.practicum.requests.service;

import ru.practicum.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> get(int userId);

    ParticipationRequestDto create(int userId, int eventId);

    ParticipationRequestDto cancel(int userId, int requestId);
}
