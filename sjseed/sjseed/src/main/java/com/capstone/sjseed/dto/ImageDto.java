package com.capstone.sjseed.dto;

public record ImageDto(
        String url
) {
    public static ImageDto of(String url) {
        return new ImageDto(url);
    }
}
