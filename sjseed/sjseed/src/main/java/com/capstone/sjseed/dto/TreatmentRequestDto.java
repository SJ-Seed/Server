package com.capstone.sjseed.dto;

public record TreatmentRequestDto(
        String image_path,
        String temperature,
        String humidity
) {
    public static TreatmentRequestDto of(String image_path, String temperature, String humidity) {
        return new TreatmentRequestDto(image_path, temperature, humidity);
    }
}
