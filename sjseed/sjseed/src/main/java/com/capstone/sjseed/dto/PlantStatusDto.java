package com.capstone.sjseed.dto;

public record PlantStatusDto(
        double avgTemp,
        double avgHum,
        double maxTemp,
        double minTemp,
        double maxHum,
        double minHum,
        double lastTemp,
        double lastHum
) {
    public static PlantStatusDto of(double avgTemp, double avgHum, double maxTemp, double minTemp, double maxHum, double minHum, double lastTemp, double lastHum) {
        return new PlantStatusDto(avgTemp, avgHum, maxTemp, minTemp, maxHum, minHum, lastTemp, lastHum);
    }
}
