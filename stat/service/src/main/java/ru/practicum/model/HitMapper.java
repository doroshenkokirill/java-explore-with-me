package ru.practicum.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Hit toHit(HitDto hitDto) {
        LocalDateTime localDateTime = LocalDateTime.parse(hitDto.getTimestamp(), formatter);
        return Hit.builder()
                .id(hitDto.getId())
                .uri(hitDto.getUri())
                .app(hitDto.getApp())
                .ip(hitDto.getIp())
                .timestamp(localDateTime)
                .build();
    }
}
