package com.capstone.sjseed.dto;

public record RandomResultDto(
        boolean ifNotLose,
        String name
) {
    public static RandomResultDto of(boolean ifNotLose, String name) {
        return new RandomResultDto(ifNotLose, name);
    }
}
