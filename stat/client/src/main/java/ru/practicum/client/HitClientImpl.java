package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;

import java.util.List;

@Component
public class HitClientImpl implements HitClient {

    private final WebClient webClient;

    @Autowired
    public HitClientImpl(WebClient.Builder webClientBuilder, @Value("${stat.url}") String url) {
        webClient = webClientBuilder.baseUrl(url).build();
    }

    @Override
    public void addHit(HitDto hitDto) {
        webClient.post()
                .uri("/hit")
                .bodyValue(hitDto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public List<HitStatDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        String urisParam = uris != null && !uris.isEmpty() ? String.join(",", uris) : "";
        String uniqueParam = (unique != null) ? unique.toString() : "false";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", urisParam)
                        .queryParam("unique", uniqueParam)
                        .build())
                .retrieve()
                .bodyToFlux(HitStatDto.class)
                .collectList()
                .block();
    }
}