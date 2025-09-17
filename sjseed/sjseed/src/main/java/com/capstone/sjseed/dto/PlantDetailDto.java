package com.capstone.sjseed.dto;

import java.util.Date;

public record PlantDetailDto(
        String name,
        String species,
        Date broughtDate,
        String description,
        String properTemp,
        String properHum,
        String properSoil,
        int period
) {
    public static PlantDetailDto of(String name, String species, Date broughtDate, String description, String properTemp, String properHum, String properSoil, int period) {
        return new PlantDetailDto(name, species, broughtDate, description, properTemp, properHum, properSoil, period);
    }
}
