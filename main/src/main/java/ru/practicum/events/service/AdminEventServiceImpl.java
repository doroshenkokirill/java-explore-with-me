package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventMapper;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.exeptions.BadRequestException;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private static final Logger log = LoggerFactory.getLogger(AdminEventServiceImpl.class);
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {

        log.info("Start checks");
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

        List<EventFullDto> result = events.stream().map(EventMapper::toEventFullDto).toList();
        log.info("Get events: {}", result);
        return result;
    }

    @Override
    public EventFullDto updateEvent(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }
}
