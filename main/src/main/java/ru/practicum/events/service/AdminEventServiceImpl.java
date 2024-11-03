package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.client.HitClient;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.locations.model.LocationMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventMapper;
import ru.practicum.events.model.enums.AdminEventStateAction;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.BadRequestException;
import ru.practicum.exeptions.ConflictException;
import ru.practicum.exeptions.NotFoundException;
import ru.practicum.dto.HitStatDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final HitClient hitClient;

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {

        log.info("Start checks");
        rangeStart = (rangeStart != null) ? rangeStart : LocalDateTime.of(1990, 1, 1, 0, 0);
        rangeEnd = (rangeEnd != null) ? rangeEnd : LocalDateTime.of(2200, 1, 1, 0, 0);

        if (categories != null) {
            categories.forEach(categoryId -> {
                if (!categoryRepository.existsById(categoryId)) {
                    throw new BadRequestException(String.format("Category with id %d is not found.", categoryId));
                }
            });
        }
        log.info("Check categories completed");

        PageRequest pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAllForAdmin(users, states, categories, rangeStart, rangeEnd, pageable);

        List<EventFullDto> result = events.stream()
                .map(this::convertToEventFullDtoWithViews)
                .toList();
        log.info("Get events: {}", result);
        return result;
    }

    @Override
    public EventFullDto updateEvent(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Start update event");
        Event oldEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id %d is not found.", eventId)));

        handleStateUpdate(updateEventAdminRequest.getStateAction(), oldEvent);
        updateEventFields(updateEventAdminRequest, oldEvent);

        EventFullDto result = EventMapper.toEventFullDto(eventRepository.save(oldEvent));
        log.info("Updated event: {}", result);
        return result;
    }

    private void handleStateUpdate(AdminEventStateAction newStateAction, Event oldEvent) {
        log.info("Start update state");
        if (newStateAction == null) {
            return;
        }

        if (newStateAction.equals(AdminEventStateAction.PUBLISH_EVENT)) {
            if (!oldEvent.getState().equals(EventState.PENDING)) {
                throw new ConflictException("The event can be published only if it is in a PENDING state.");
            }
            oldEvent.setState(EventState.PUBLISHED);
            oldEvent.setPublishedOn(LocalDateTime.now());
        } else if (newStateAction.equals(AdminEventStateAction.REJECT_EVENT)) {
            if (oldEvent.getState().equals(EventState.PUBLISHED)) {
                throw new ConflictException("The event can be canceled only if it has not yet been PUBLISHED.");
            }
            oldEvent.setState(EventState.CANCELED);
            log.info("Updated event state: {}", oldEvent);
        }
    }

    private void updateEventFields(UpdateEventAdminRequest updateRequest, Event event) {
        log.info("Start update event fields");
        if (updateRequest.getEventDate() != null) {
            updateEventDate(updateRequest.getEventDate(), event);
        }

        Optional.ofNullable(updateRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updateRequest.getCategory()).ifPresent(categoryId -> {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id %d is not found.", categoryId)));
            event.setCategory(category);
        });
        Optional.ofNullable(updateRequest.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updateRequest.getLocation()).ifPresent(location -> event.setLocation(LocationMapper.toLocation(location)));
        Optional.ofNullable(updateRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(updateRequest.getRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(updateRequest.getTitle()).ifPresent(event::setTitle);
        log.info("End update event fields");
    }

    private void updateEventDate(LocalDateTime newEventDate, Event event) {
        log.info("Start update event date");
        if (newEventDate.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new BadRequestException("The updated event time must be at least one hour from the current time.");
        }
        event.setEventDate(newEventDate);
        log.info("Updated event date: {}", event);
    }

    private EventFullDto convertToEventFullDtoWithViews(Event event) {
        Long views = getViews(event);
        event.setViews(views);
        return EventMapper.toEventFullDto(event);
    }

    private Long getViews(Event event) {
        String uris = "/events/" + event.getId();
        LocalDateTime start = event.getPublishedOn() != null ? event.getPublishedOn() : event.getCreatedOn();
        LocalDateTime end = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startFormatted = start.format(formatter);
        String endFormatted = end.format(formatter);

        ResponseEntity<Object> response = hitClient.getStats(startFormatted, endFormatted, List.of(uris), true);

        if (response.getBody() instanceof List<?>) {
            return ((List<?>) response.getBody()).stream()
                    .filter(HitStatDto.class::isInstance)
                    .map(HitStatDto.class::cast)
                    .findFirst()
                    .map(HitStatDto::getHits)
                    .orElse(0L);
        }
        return 0L;
    }
}
