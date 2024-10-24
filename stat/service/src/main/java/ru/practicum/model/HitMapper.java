package ru.practicum.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;

@UtilityClass
public class HitMapper {

    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .id(hitDto.getId())
                .uri(hitDto.getUri())
                .app(hitDto.getApp())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }
}
