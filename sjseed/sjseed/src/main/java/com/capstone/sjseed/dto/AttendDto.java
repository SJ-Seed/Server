package com.capstone.sjseed.dto;

public record AttendDto(
        Boolean[] attendedDays,
        int rewardedCoin,
        int totalCoin
) {
    public static AttendDto of(Boolean[] attendedDays, int rewardedCoin, int  totalCoin) {
        return new AttendDto(attendedDays, rewardedCoin,  totalCoin);
    }
}
