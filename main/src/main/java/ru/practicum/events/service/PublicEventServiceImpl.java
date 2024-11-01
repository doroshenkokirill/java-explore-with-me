package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventMapper;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getEventsList(String text, Set<Integer> categories, Boolean paid,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                             Boolean onlyAvailable, String sort, int from, int size) {
        if (categories != null) {
            categories.forEach(id -> checkId(id, eventRepository));
        }

        rangeStart = (rangeStart != null) ? rangeStart : LocalDateTime.MIN;
        rangeEnd = (rangeEnd != null) ? rangeEnd : LocalDateTime.MAX;
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
        //TODO
        EventFullDto result = EventMapper.toEventFullDto(eventRepository.save(event));
        log.info("Result of get event by Id {}: {}", id, result);
        return result;
    }

    private void checkId(int id, JpaRepository<?, Integer> repository) {
        if (!repository.existsById(id)) {
            log.info("Not found with id: {}", id);
            throw new NotFoundException("Not found with id " + id + " from: " + repository);
        }
    }
}
