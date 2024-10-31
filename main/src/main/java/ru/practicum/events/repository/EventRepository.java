package ru.practicum.events.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e " +
            "WHERE (:users IS NULL OR :#{#users.isEmpty()} = true OR e.initiator.id IN :users) " +
            "AND (:states IS NULL OR :#{#states.isEmpty()} = true OR e.state IN :states) " +
            "AND (:categories IS NULL OR :#{#categories.isEmpty()} = true OR e.category.id IN :categories) " +
            "AND (e.eventDate BETWEEN :rangeStart AND :rangeEnd)")
    List<Event> findAllForAdmin(List<Integer> users, List<EventState> states, List<Integer> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, PageRequest pageable);
}
