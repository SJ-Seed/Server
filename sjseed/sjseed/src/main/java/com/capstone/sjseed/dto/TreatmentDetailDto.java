package com.capstone.sjseed.dto;

public record TreatmentDetailDto(
        Long plantId,
        Long speciesId,
        String disease,
        String explain,
        String cause,
        String cure
) {
    public static TreatmentDetailDto of(Long plantId, Long speciesId, String disease, String explain, String cause, String cure) {
        return new TreatmentDetailDto(plantId, speciesId, disease, explain, cause, cure);
    }
}
