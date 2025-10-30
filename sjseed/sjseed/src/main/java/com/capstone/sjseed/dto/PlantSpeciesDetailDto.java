package com.capstone.sjseed.dto;

import java.time.LocalDate;

public record PlantSpeciesDetailDto(
        String name,
        String properTemp,
        String properHum,
        String process,
        String water,
        String description
) {
    public static PlantSpeciesDetailDto of(String name, String properTemp, String properHum, String process, String water, String description) {
        return new PlantSpeciesDetailDto(name, properTemp, properHum, process, water, description);
    }
}
