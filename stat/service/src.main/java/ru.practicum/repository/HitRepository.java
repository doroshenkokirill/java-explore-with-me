package service.src.main.java.ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.src.main.java.ru.practicum.model.Hit;

public class HitRepository extends JpaRepository<Hit, Integer> {
}
