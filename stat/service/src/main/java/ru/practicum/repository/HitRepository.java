package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.HitStatDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface HitRepository extends JpaRepository<Hit, Integer> {

    @Query("SELECT new ru.practicum.dto.HitStatDto(h.app, h.uri, COUNT (DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT (DISTINCT h.ip) DESC ")
    List<HitStatDto> findAllUniqueHitsWhenUriIsEmpty(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.HitStatDto(h.app, h.uri, COUNT (DISTINCT h.ip)) "
            + "FROM Hit h "
            + "WHERE h.timestamp BETWEEN :start AND :end "
            + "AND h.uri IN (:uris)"
            + "GROUP BY h.app, h.uri "
            + "ORDER BY COUNT (DISTINCT h.ip) DESC ")
    List<HitStatDto> findAllUniqueHitsWhenUriIsNotEmpty(
            LocalDateTime start, LocalDateTime end, Set<String> uris);

    @Query("SELECT new ru.practicum.dto.HitStatDto(h.app, h.uri, COUNT (h.ip))" +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT (h.ip) DESC ")
    List<HitStatDto> findAllHitsWhenUriIsEmpty(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.dto.HitStatDto(h.app, h.uri, count (h.ip)) "
            + "FROM Hit h "
            + "WHERE h.timestamp BETWEEN :start AND :end "
            + "AND h.uri in (:uris)"
            + "GROUP BY h.app, h.uri "
            + "ORDER BY COUNT (h.ip) DESC ")
    List<HitStatDto> findAllHitsWhenStarEndUris(LocalDateTime start, LocalDateTime end, Set<String> uris);
}
