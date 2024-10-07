package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.HitStatDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Integer> {

    @Query("SELECT new ru.practicum.dto.HitStatDto(app, uri, COUNT(ip)) " +
            "FROM Hit " +
            "WHERE timestamp BETWEEN :start AND :end " +
            "AND uri IN (:uris) " +
            "GROUP BY app, uri ")
    List<HitStatDto> findAllHits(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.dto.HitStatDto(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM Hit AS hit " +
            "WHERE hit.timestamp BETWEEN :start AND :end " +
            "AND (:uris IS NULL OR hit.uri IN :uris) " +
            "GROUP BY hit.app, hit.uri " +
            "ORDER BY COUNT(DISTINCT hit.ip) DESC")
    List<HitStatDto> findUniqueHits(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris);
}
