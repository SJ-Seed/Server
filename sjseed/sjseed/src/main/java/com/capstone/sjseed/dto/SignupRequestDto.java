package com.capstone.sjseed.dto;

public record SignupRequestDto(
        String name,
        int year,
        String loginId,
        String password,
        String phoneNumber
) {

    public static SignupRequestDto of(String name, int year, String loginId, String password, String phoneNumber) {
        return new SignupRequestDto(name, year, loginId, password, phoneNumber);
    }
}
