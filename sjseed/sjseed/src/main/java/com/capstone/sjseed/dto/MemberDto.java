package com.capstone.sjseed.dto;

public record MemberDto(
        Long memberId,
        String name,
        String loginId,
        String phoneNumber
) {
    public static MemberDto of(Long memberId, String name, String loginId, String phoneNumber) {
        return new MemberDto(memberId, name, loginId, phoneNumber);
    }
}
