package com.capstone.sjseed.dto;

public record RandomResultDto(
        boolean ifNotLose,
        String name,
        Long pieceId
) {
    public static RandomResultDto of(boolean ifNotLose, String name, Long pieceId) {
        return new RandomResultDto(ifNotLose, name, pieceId);
    }
}
