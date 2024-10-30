package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.requests.model.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}