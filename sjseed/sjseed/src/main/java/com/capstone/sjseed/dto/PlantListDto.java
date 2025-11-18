package com.capstone.sjseed.dto;

import java.time.LocalDate;

public record PlantListDto(
        Long plantId,
        String name,
        LocalDate broughtDate,
        boolean diseased,
        String species,
        Long speciesId
) {

    public static PlantListDto of(Long plantId, String name, LocalDate broughtDate, boolean diseased, String species, Long speciesId) {
        return new PlantListDto(plantId, name, broughtDate, diseased, species, speciesId);
    }
}
