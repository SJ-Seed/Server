package com.capstone.sjseed.dto;

public record SignUpRequestDto(
        String name,
        int year,
        String loginId,
        String password,
        String phoneNumber
) {

    public static SignUpRequestDto of(String name, int year, String loginId, String password, String phoneNumber) {
        return new SignUpRequestDto(name, year, loginId, password, phoneNumber);
    }
}
