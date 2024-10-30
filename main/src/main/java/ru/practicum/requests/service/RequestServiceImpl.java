package ru.practicum.requests.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.repository.RequestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private static final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);
    private final RequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> get(int userId) {
        return List.of();
    }

    @Override
    public ParticipationRequestDto create(int userId, int eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancel(int userId, int requestId) {
        return null;
    }
}
