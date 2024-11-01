package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.requests.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByRequesterId(int userId);

    List<Request> findAllByEventId(int eventId);

    boolean existsByRequesterIdAndEventId(int requesterId, int eventId);
}