package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.client.HitClientImpl;
import ru.practicum.dto.HitStatDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventMapper;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.BadRequestException;
import ru.practicum.exeptions.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final HitClientImpl hitClient;

    @Override
    public List<EventShortDto> getEventsList(String text, Set<Integer> categories, Boolean paid,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                             Boolean onlyAvailable, String sort, int from, int size) {
        if (categories != null) {
            categories.forEach(id -> checkId(id, categoryRepository));
        }

        rangeStart = (rangeStart != null) ? rangeStart : LocalDateTime.of(1990, 1, 1, 0, 0);
        rangeEnd = (rangeEnd != null) ? rangeEnd : LocalDateTime.of(2200, 1, 1, 0, 0);
        Sort sortCriteria = "VIEWS".equalsIgnoreCase(sort)
                ? Sort.by(Sort.Direction.DESC, "views")
                : Sort.by(Sort.Direction.ASC, "eventDate");

        Pageable pageable = PageRequest.of(from / size, size, sortCriteria);

        List<Event> events = (text == null || text.isEmpty())
                ? eventRepository.findAllWithoutText(categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable)
                : eventRepository.findAllWithText(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable);

        List<EventShortDto> result = events.stream()
                .peek(event -> event.setViews(event.getViews()))
                .map(EventMapper::toEventShortDto).toList();
        log.info("Events list: {}", result);
        return result;
    }

    @Override
    public EventFullDto get(int id) {
        checkId(id, eventRepository);
        Event event = eventRepository.findById(id).get();

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException(String.format("There are no published events with id %d", id));
        }
        event.setViews(getViews(event) + 1);
        EventFullDto result = EventMapper.toEventFullDto(eventRepository.save(event));
        log.info("Result of get event by Id {}: {}", id, result);
        return result;
    }

    private void checkId(int id, JpaRepository<?, Integer> repository) {
        if (id <= 0) {
            throw new BadRequestException(String.format("Id %d must be > 0", id));
        }
        if (!repository.existsById(id)) {
            log.info("Not found with id: {}", id);
            throw new NotFoundException("Not found with id " + id + " from: " + repository);
        }
    }

    private Long getViews(Event event) {
        String uris = "/events/" + event.getId();
        LocalDateTime start = event.getPublishedOn() != null ? event.getPublishedOn() : event.getCreatedOn();
        LocalDateTime end = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startFormatted = start.format(formatter);
        String endFormatted = end.format(formatter);

        List<HitStatDto> hitStatDtoList = hitClient.getStats(startFormatted, endFormatted, List.of(uris), true);
        return hitStatDtoList.stream().findFirst().map(HitStatDto::getHits).orElse(0L);
    }
}
