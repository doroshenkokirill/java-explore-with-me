package ru.practicum.model;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.HitStatDto;

import java.sql.Timestamp;

@UtilityClass
public class HitMapper {
    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .id(hitDto.getId())
                .uri(hitDto.getUri())
                .app(hitDto.getApp())
                .ip(hitDto.getIp())
                .timestamp(Timestamp.valueOf(hitDto.getTimestamp()))
                .build();
    }

    public static HitStatDto toHitStatDto(Hit hit) {
        HitStatDto hitStatDto = new HitStatDto();
        hitStatDto.setApp(hit.getApp());
        hitStatDto.setUri(hit.getUri());
        return hitStatDto;
    }
}
