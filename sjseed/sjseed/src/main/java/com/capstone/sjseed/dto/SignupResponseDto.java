package com.capstone.sjseed.dto;

public record SignupResponseDto(
        String name,
        int year,
        String loginId,
        String encodedPassword,
        String phoneNumber
) {
    public static SignupResponseDto of(String name, int year, String loginId, String encodedPassword, String phoneNumber) {
        return new SignupResponseDto(name, year, loginId, encodedPassword, phoneNumber);
    }
}
