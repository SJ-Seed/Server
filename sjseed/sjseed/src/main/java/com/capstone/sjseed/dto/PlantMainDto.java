package com.capstone.sjseed.dto;

public record PlantMainDto(
        String name,
        double temperature,
        double humidity,
        double soilWater,
        Long speciesId
) {
    public static PlantMainDto of(String name, double temperature, double humidity, double soilWater, Long speciesId) {
        return new PlantMainDto(name, temperature, humidity, soilWater, speciesId);
    }
}
