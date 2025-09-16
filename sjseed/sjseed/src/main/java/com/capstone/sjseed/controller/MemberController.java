package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.SignupRequestDto;
import com.capstone.sjseed.dto.SignupResponseDto;
import com.capstone.sjseed.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signUp(@RequestBody SignupRequestDto signupDto) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.signUp(signupDto)));
    }
}
