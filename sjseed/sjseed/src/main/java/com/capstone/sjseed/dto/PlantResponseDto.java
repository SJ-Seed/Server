package com.capstone.sjseed.dto;


import java.time.LocalDate;
import java.util.Date;

public record PlantResponseDto(
        Long id,
        String name,
        LocalDate broughtDate,
        Long memberId
) {

    public static PlantResponseDto of(Long id, String name, LocalDate broughtDate, Long memberId) {
        return new PlantResponseDto(id, name, broughtDate, memberId);
    }
}
