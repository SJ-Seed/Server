package com.capstone.sjseed.dto;

import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.PlantSpecies;

import java.time.LocalDate;
import java.util.Date;

public record PlantResponseDto(
        Long id,
        String name,
        PlantSpecies species,
        LocalDate broughtDate,
        Long memberId
) {

    public static PlantResponseDto of(Long id, String name, PlantSpecies species, LocalDate broughtDate, Long memberId) {
        return new PlantResponseDto(id, name, species, broughtDate, memberId);
    }
}
