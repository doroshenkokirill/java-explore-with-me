package ru.practicum.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
