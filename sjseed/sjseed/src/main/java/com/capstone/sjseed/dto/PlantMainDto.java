package com.capstone.sjseed.dto;

public record PlantMainDto(
        String name,
        double temperature,
        double humidity,
        double soilWater
) {
    public static PlantMainDto of(String name, double temperature, double humidity, double soilWater) {
        return new PlantMainDto(name, temperature, humidity, soilWater);
    }
}
