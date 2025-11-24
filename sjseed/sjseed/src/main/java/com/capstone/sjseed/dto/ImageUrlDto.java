package com.capstone.sjseed.dto;

public record ImageUrlDto(
        String url
) {
    public static ImageUrlDto of(String url) {
        return new ImageUrlDto(url);
    }
}
