package com.capstone.sjseed.dto;

public record PlantMainDto(
        Long plantId,
        String name,
        double temperature,
        double humidity,
        double soilWater,
        Long speciesId
) {
    public static PlantMainDto of(Long plantId, String name, double temperature, double humidity, double soilWater, Long speciesId) {
        return new PlantMainDto(plantId, name, temperature, humidity, soilWater, speciesId);
    }
}
