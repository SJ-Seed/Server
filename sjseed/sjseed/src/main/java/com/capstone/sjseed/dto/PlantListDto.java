package com.capstone.sjseed.dto;

import java.time.LocalDate;

public record PlantListDto(
        String name,
        LocalDate broughtDate,
        boolean diseased,
        String species,
        Long speciesId
) {

    public static PlantListDto of(String name, LocalDate broughtDate, boolean diseased, String species, Long speciesId) {
        return new PlantListDto(name, broughtDate, diseased, species, speciesId);
    }
}
