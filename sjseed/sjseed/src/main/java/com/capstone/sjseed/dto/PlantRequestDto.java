package com.capstone.sjseed.dto;

public record PlantRequestDto(
        String name,
        String code
) {
    public static PlantRequestDto of(String name, String code) {
        return new PlantRequestDto(name, code);
    }
}
