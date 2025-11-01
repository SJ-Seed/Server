package com.capstone.sjseed.dto;

public record SignUpResponseDto(
        String name,
        int year,
        String loginId,
        String encodedPassword,
        String phoneNumber
) {
    public static SignUpResponseDto of(String name, int year, String loginId, String encodedPassword, String phoneNumber) {
        return new SignUpResponseDto(name, year, loginId, encodedPassword, phoneNumber);
    }
}
