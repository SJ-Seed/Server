package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.SignupRequestDto;
import com.capstone.sjseed.dto.SignupResponseDto;
import com.capstone.sjseed.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signUp(@RequestBody SignupRequestDto signupDto) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.signUp(signupDto)));
    }

    @Operation(summary = "코인 개수 조회", description = "멤버의 코인 개수를 조회합니다.")
    @GetMapping("/coin/{memberId}")
    public ResponseEntity<ApiResponse<Integer>> getCoin(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getCoin(memberId)));
    }
}
