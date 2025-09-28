package com.capstone.sjseed.dto;

import java.util.Date;

public record PlantListDto(
        String name,
        Date broughtDate,
        boolean diseased,
        String species
) {

    public static PlantListDto of(String name, Date broughtDate, boolean diseased, String species) {
        return new PlantListDto(name, broughtDate, diseased, species);
    }
}
