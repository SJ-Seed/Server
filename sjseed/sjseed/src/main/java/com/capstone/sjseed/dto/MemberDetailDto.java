package com.capstone.sjseed.dto;

public record MemberDetailDto(
        String name,
        int plantNum,
        int pieceNum
) {
    public static MemberDetailDto of(String name, int plantNum, int pieceNum) {
        return new MemberDetailDto(name, plantNum, pieceNum);
    }
}
