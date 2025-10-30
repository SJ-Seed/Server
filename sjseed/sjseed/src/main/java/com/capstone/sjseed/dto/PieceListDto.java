package com.capstone.sjseed.dto;

public record PieceListDto(
        String name,
        int rarity
) {
    public static PieceListDto of(String name, int rarity) {
        return new PieceListDto(name, rarity);
    }
}
