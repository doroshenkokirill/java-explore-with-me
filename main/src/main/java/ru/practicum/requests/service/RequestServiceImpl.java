package ru.practicum.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.ConflictException;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.Request;
import ru.practicum.requests.model.RequestMapper;
import ru.practicum.requests.model.RequestStatus;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.users.model.User;
import ru.practicum.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> get(int userId) {
        checkId(userId, userRepository);

        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        List<ParticipationRequestDto> result = requests.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .toList();

        log.info("List of requests: {} by ID {}", result, userId);

        return result;
    }

    @Override
    public ParticipationRequestDto create(int userId, int eventId) {
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);

        User user = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Невозможно добавить запрос на участие в событии, созданном пользователем.");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Невозможно участвовать в неопубликованном событии.");
        }
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException(String.format("Пользователь с id %d уже участвует в событии с id %d.", userId, eventId));
        }
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Нет свободных мест в событии.");
        }

        RequestStatus status = (!event.getRequestModeration() || event.getParticipantLimit() == 0)
                ? RequestStatus.CONFIRMED : RequestStatus.PENDING;

        if (status == RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }

        Request request = Request.builder()
                .created(LocalDateTime.now().withNano(0))
                .event(event)
                .requester(user)
                .status(status)
                .build();

        ParticipationRequestDto result = RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        log.info("Created request: {}", result);
        return result;
    }

    @Override
    public ParticipationRequestDto cancel(int userId, int requestId) {
        checkId(userId, userRepository);
        checkId(requestId, requestRepository);

        Request requestToSave = requestRepository.findById(requestId).get();
        requestToSave.setStatus(RequestStatus.CANCELED);

        Request result = requestRepository.save(requestToSave);
        ParticipationRequestDto participationRequestDto = RequestMapper.toParticipationRequestDto(result);

        log.info("Canceled request: {}", participationRequestDto.toString());
        return participationRequestDto;
    }

    private void checkId(int id, JpaRepository<?, Integer> repository) {
        if (!repository.existsById(id)) {
            log.info("Not found with id: {}", id);
            throw new NotFoundException("Not found with id " + id + " from: " + repository);
        }
    }
}
