package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.HitDto;

import java.util.List;
import java.util.Map;

@Service
public class HitClient extends BaseClient {
    @Autowired
    public HitClient(@Value("${explorewithme-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addHit(HitDto hitDto) {
        return post("/hit", hitDto);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {
        String urisParam = uris != null && !uris.isEmpty() ? String.join(",", uris) : "";
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", urisParam,
                "unique", unique
        );

        return get("/stats" + "?start=" + start + "&end=" + end + "&uris=" + uris + "&unique=" + unique, parameters);
    }

}