package com.capstone.sjseed.dto;

import java.time.LocalDate;

public record PlantListDto(
        String name,
        LocalDate broughtDate,
        boolean diseased,
        String species
) {

    public static PlantListDto of(String name, LocalDate broughtDate, boolean diseased, String species) {
        return new PlantListDto(name, broughtDate, diseased, species);
    }
}
