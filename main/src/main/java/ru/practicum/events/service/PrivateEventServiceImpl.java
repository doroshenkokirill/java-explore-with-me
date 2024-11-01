package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.dto.*;
import ru.practicum.events.locations.dto.LocationDto;
import ru.practicum.events.locations.model.LocationMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventMapper;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.model.enums.UserEventStateAction;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.BadRequestException;
import ru.practicum.exeptions.ConflictException;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.model.Request;
import ru.practicum.requests.model.RequestMapper;
import ru.practicum.requests.model.RequestStatus;
import ru.practicum.requests.repository.RequestRepository;
import ru.practicum.users.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<EventShortDto> getAll(int userId, int from, int size) {
        checkId(userId, userRepository);

        List<Event> events = eventRepository.findAllByInitiatorId(userId);
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Event event : events) {
            eventShortDtoList.add(EventMapper.toEventShortDto(event));
        }
        List<EventShortDto> result = eventShortDtoList.stream().skip(from).limit(size).toList();
        log.info("Get all events from {} to {}: {}", from, size, result);
        return result;
    }

    @Override
    public EventFullDto create(int userId, NewEventDto newEventDto) {
        checkId(userId, userRepository);
        checkEventDate(newEventDto.getEventDate());

        Event event = EventMapper.toEventDto(newEventDto);
        event.setCreatedOn(LocalDateTime.now());
        event.setConfirmedRequests(0);
        event.setInitiator(userRepository.findById(userId).get());
        event.setState(EventState.PENDING);

        EventFullDto result = EventMapper.toEventFullDto(eventRepository.save(event));
        log.info("Create new event: {}", result);
        return result;
    }

    @Override
    public EventFullDto getByEventId(int userId, int eventId) {
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId);
        checkUserIdAndInitiatorId(userId, event);

        EventFullDto result = EventMapper.toEventFullDto(event);
        log.info("Get event: {}", result);
        return result;
    }

    @Override
    public EventFullDto update(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);
        Event event = eventRepository.findById(eventId).get();
        checkUserIdAndInitiatorId(userId, event);

        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Only PENDING or CANCELED events could be updated.");
        }

        LocalDateTime newEventDate = updateEventUserRequest.getEventDate();
        if (newEventDate != null) {
            checkEventDate(newEventDate);
            event.setEventDate(newEventDate);
        }

        updateFieldIfNotNull(updateEventUserRequest.getAnnotation(), event::setAnnotation);
        updateFieldIfNotNull(updateEventUserRequest.getDescription(), event::setDescription);
        updateFieldIfNotNull(updateEventUserRequest.getTitle(), event::setTitle);
        updateFieldIfNotNull(updateEventUserRequest.getPaid(), event::setPaid);
        updateFieldIfNotNull(updateEventUserRequest.getParticipantLimit(), event::setParticipantLimit);
        updateFieldIfNotNull(updateEventUserRequest.getRequestModeration(), event::setRequestModeration);

        Integer newCategoryDtoId = updateEventUserRequest.getCategory();
        if (newCategoryDtoId != null) {
            Category category = categoryRepository.findById(newCategoryDtoId).orElseThrow(() -> new NotFoundException("Category not found"));
            event.setCategory(category);
        }

        LocationDto newLocation = updateEventUserRequest.getLocation();
        if (newLocation != null) {
            event.setLocation(LocationMapper.toLocation(newLocation));
        }

        UserEventStateAction newStateAction = updateEventUserRequest.getStateAction();
        if (newStateAction != null) {
            if (newStateAction.equals(UserEventStateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            } else if (newStateAction.equals(UserEventStateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            }
        }

        EventFullDto result = EventMapper.toEventFullDto(eventRepository.save(event));
        log.info("Update event: {}", result);
        return result;
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId) {
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);

        Event event = eventRepository.findById(eventId).get();
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException(String.format("User with id %d is not initiator of event with id %d", userId, event.getId()));
        }

        List<Request> requests = requestRepository.findAllByEventId(eventId);
        List<ParticipationRequestDto> result = requests.stream()
                .map(RequestMapper::toParticipationRequestDto).toList();
        log.info("Get requests by user: {}", result);
        return result;
    }

    @Override
    public EventRequestStatusUpdateResult updateStatus(int userId, int eventId, EventRequestStatusUpdateRequest e) {
        checkId(userId, userRepository);
        checkId(eventId, eventRepository);

        Event event = eventRepository.findById(eventId).get();
        checkInitiatorIdWithEvent(userId, event);

        int limit = event.getParticipantLimit();
        checkLimit(limit, event);

        RequestStatus status = e.getStatus();

        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();


        List<Request> requests = requestRepository.findAllById(e.getRequestIds());
        requests.forEach(request -> {
            if (request.getStatus().equals(RequestStatus.PENDING)) {
                if (limit == 0) {
                    request.setStatus(RequestStatus.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                } else if (limit > event.getConfirmedRequests()) {
                    if (!event.getRequestModeration() || status.equals(RequestStatus.CONFIRMED)) {
                        request.setStatus(RequestStatus.CONFIRMED);
                        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        confirmed.add(RequestMapper.toParticipationRequestDto(request));
                    } else {
                        request.setStatus(RequestStatus.REJECTED);
                        rejected.add(RequestMapper.toParticipationRequestDto(request));
                    }
                    requestRepository.save(request);
                }
            } else {
                throw new ConflictException("Status is not PENDING.");
            }
        });

        eventRepository.save(event);
        EventRequestStatusUpdateResult result = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed).rejectedRequests(rejected).build();
        log.info("Updated: {}", result);
        return result;
    }

    private void checkId(int id, JpaRepository<?, Integer> repository) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Not found with id " + id + " from: " + repository);
        }
    }

    private void checkUserIdAndInitiatorId(int userId, Event event) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ConflictException(String.format("User is Initiator %d = %d", userId, event.getId()));
        }
    }

    private <T> void updateFieldIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    private void checkEventDate(LocalDateTime newEventDate) {
        if (Duration.between(LocalDateTime.now(),
                Timestamp.valueOf(newEventDate).toLocalDateTime()).toHours() <= 2) {
            throw new BadRequestException("The difference should be more than 1 hour");
        }
    }

    private void checkInitiatorIdWithEvent(int initiatorId, Event event) {
        if (!event.getInitiator().getId().equals(initiatorId)) {
            throw new ConflictException(String.format("User not Initiator of event %d != %d", initiatorId, event.getId()));
        }
    }

    private void checkLimit(int limit, Event event) {
        if (limit != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Participant limit exceeded");
        }
    }
}
