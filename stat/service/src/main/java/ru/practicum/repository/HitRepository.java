package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.HitStatDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Integer> {

    @Query("select new ru.practicum.dto.HitStatDto(h.app, h.uri, count (distinct h.ip)) " +
            "from Hit h " +
            "where h.timestamp between :start and :end " +
            "group by h.app, h.uri " +
            "order by count (distinct h.ip) desc")
    List<HitStatDto> findAllUniqueHitsWhenUriIsEmpty(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.HitStatDto(h.app, h.uri, count (distinct h.ip)) "
            + "from Hit h "
            + "where h.timestamp between :start and :end "
            + "and h.uri in (:uris)"
            + "group by h.app, h.uri "
            + "order by count (distinct h.ip) desc ")
    List<HitStatDto> findAllUniqueHitsWhenUriIsNotEmpty(
            LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.dto.HitStatDto(h.app, h.uri, count (h.ip))" +
            "from Hit h " +
            "where h.timestamp between :start and :end " +
            "group by h.app, h.uri " +
            "order by count (h.ip) desc")
    List<HitStatDto> findAllHitsWhenUriIsEmpty(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.dto.HitStatDto(h.app, h.uri, count (h.ip)) "
            + "from Hit h "
            + "where h.timestamp between :start and :end "
            + "and h.uri in (:uris)"
            + "group by h.app, h.uri "
            + "order by count (h.ip) desc")
    List<HitStatDto> findAllHitsWhenStarEndUris(LocalDateTime start, LocalDateTime end, List<String> uris);
}
