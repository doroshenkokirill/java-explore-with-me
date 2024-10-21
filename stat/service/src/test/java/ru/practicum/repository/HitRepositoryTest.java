/*
package ru.practicum.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;
import ru.practicum.model.Hit;
import ru.practicum.model.HitMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HitRepositoryTest {

    @Autowired
    private HitRepository hitRepository;

    private final LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
    private final LocalDateTime start2 = LocalDateTime.of(2023, 1, 1, 1, 0);

    private final LocalDateTime end = LocalDateTime.of(2023, 12, 1, 0, 0);

    private final List<String> uris = List.of("/hits");

    HitDto hitDto = HitDto.builder()
            .ip("123.123.123.00")
            .app("ewm")
            .uri("/hits")
            .timestamp(start)
            .build();
    Hit hit = HitMapper.toHit(hitDto);
    HitDto hitDto2 = HitDto.builder()
            .ip("123.123.124.00")
            .app("ewm")
            .uri("/hits")
            .timestamp(start2)
            .build();
    Hit hit2 = HitMapper.toHit(hitDto2);
    HitDto hitDto3 = HitDto.builder()
            .ip("123.123.123.00")
            .app("ewm")
            .uri("/123")
            .timestamp(start)
            .build();
    Hit hit3 = HitMapper.toHit(hitDto3);

    @BeforeEach
    public void setUp() {
        hitRepository.deleteAll();
    }

    @Test
    public void testFindAllHits() {
        hitRepository.deleteAll();

        hitRepository.save(hit);
        hitRepository.save(hit2);

        List<Hit> savedHits = hitRepository.findAll();
        System.out.println("Saved Hits: " + savedHits);

        List<HitStatDto> results = hitRepository.findAllHitsWhenStarEndUris(start, end, (Set<String>) uris);
        System.out.println("Query Results: " + Arrays.toString(results.toArray()));

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.getFirst().getApp()).isEqualTo("ewm");
        assertThat(results.getFirst().getHits()).isEqualTo(2);
        assertThat(results.getFirst().getUri()).isEqualTo("/hits");
    }

    @Test
    public void testFindUniqueHitsTest() {
        hitRepository.deleteAll();

        hitRepository.save(hit);
        hitRepository.save(hit2);
        hitRepository.save(hit3);

        Set<Hit> savedHits = (Set<Hit>) hitRepository.findAll();
        System.out.println("Saved Hits: " + savedHits);

        List<HitStatDto> results = hitRepository.findAllHitsWhenStarEndUris(start, end, (Set<String>) uris);
        System.out.println("Query Results: " + Arrays.toString(results.toArray()));

        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.getFirst().getApp()).isEqualTo("ewm");
        assertThat(results.getFirst().getHits()).isEqualTo(2);
        assertThat(results.getFirst().getUri()).isEqualTo("/hits");

        Set<String> uris = Set.of("/123");
        List<HitStatDto> results2 = hitRepository.findAllUniqueHitsWhenUriIsNotEmpty(start, end, uris);
        System.out.println("Query Results: " + Arrays.toString(results2.toArray()));

        assertThat(results2).isNotNull();
        assertThat(results2.size()).isEqualTo(1);
        assertThat(results2.getFirst().getApp()).isEqualTo("ewm");
        assertThat(results2.getFirst().getHits()).isEqualTo(1);
        assertThat(results2.getFirst().getUri()).isEqualTo("/123");

    }
}
*/
