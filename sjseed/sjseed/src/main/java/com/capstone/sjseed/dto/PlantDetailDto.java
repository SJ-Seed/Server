package com.capstone.sjseed.dto;

import java.time.LocalDate;
import java.util.Date;

public record PlantDetailDto(
        String name,
        String species,
        LocalDate broughtDate,
        String description,
        String properTemp,
        String properHum,
        String properSoil,
        int period
) {
    public static PlantDetailDto of(String name, String species, LocalDate broughtDate, String description, String properTemp, String properHum, String properSoil, int period) {
        return new PlantDetailDto(name, species, broughtDate, description, properTemp, properHum, properSoil, period);
    }
}
