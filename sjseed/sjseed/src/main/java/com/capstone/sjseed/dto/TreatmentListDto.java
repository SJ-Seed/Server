package com.capstone.sjseed.dto;

import java.time.LocalDate;

public record TreatmentListDto(
        String plantName,
        LocalDate date,
        String disease
) {
    public static TreatmentListDto of(String plantName, LocalDate date, String disease) {
        return new TreatmentListDto(plantName, date, disease);
    }
}
