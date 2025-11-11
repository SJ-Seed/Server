package com.capstone.sjseed.dto;


import java.time.LocalDate;
import java.util.Date;

public record PlantResponseDto(
        Long id,
        String name,
        LocalDate broughtDate,
        Long memberId,
        Long speciesId
) {

    public static PlantResponseDto of(Long id, String name, LocalDate broughtDate, Long memberId, Long speciesId) {
        return new PlantResponseDto(id, name, broughtDate, memberId, speciesId);
    }
}
