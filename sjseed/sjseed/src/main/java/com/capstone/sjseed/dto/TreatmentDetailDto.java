package com.capstone.sjseed.dto;

public record TreatmentDetailDto(
        Long plantId,
        Long speciesId,
        String disease,
        String explain,
        String cause,
        String cure,
        String imageUrl
) {
    public static TreatmentDetailDto of(Long plantId, Long speciesId, String disease, String explain, String cause, String cure, String imageUrl) {
        return new TreatmentDetailDto(plantId, speciesId, disease, explain, cause, cure, imageUrl);
    }
}
