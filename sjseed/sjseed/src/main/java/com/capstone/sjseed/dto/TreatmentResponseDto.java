package com.capstone.sjseed.dto;

public record TreatmentResponseDto(
        boolean photo,
        String state,
        String message,
        String explain,
        String cause,
        String cure
) {
    public static TreatmentResponseDto of(boolean photo, String state, String message, String explain, String cause, String cure) {
        return new TreatmentResponseDto(photo, state, message, explain, cause, cure);
    }
}
