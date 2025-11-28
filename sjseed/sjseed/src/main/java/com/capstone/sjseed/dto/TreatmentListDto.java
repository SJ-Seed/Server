package com.capstone.sjseed.dto;

import java.time.LocalDate;

public record TreatmentListDto(
        String plantName,
        LocalDate date,
        String disease,
        Long plantId,
        Long speciesId,
        Long treatmentId
) {
    public static TreatmentListDto of(String plantName, LocalDate date, String disease, Long plantId, Long speciesId, Long treatmentId) {
        return new TreatmentListDto(plantName, date, disease, plantId, speciesId, treatmentId);
    }
}
