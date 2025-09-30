package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.dto.*;
import com.capstone.sjseed.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "코인 개수 조회", description = "사용자의 코인 개수를 조회합니다.")
    @GetMapping("/coin/{memberId}")
    public ResponseEntity<ApiResponse<Integer>> getCoin(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getCoin(memberId)));
    }

    @Operation(summary = "사용자의 식물 리스트 조회", description = "사용자의 식물 리스트를 조회합니다.(메인 화면에서 사용)")
    @GetMapping("/plants/{memberId}")
    public ResponseEntity<ApiResponse<List<PlantMainDto>>> getPlants(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getPlantList(memberId)));
    }

    @Operation(summary = "사용자의 출석일 조회", description = "사용자의 이번주 출석일을 조회합니다.")
    @GetMapping("/attend/{memberId}")
    public ResponseEntity<ApiResponse<Boolean[]>>  getAttendance(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getAttendedDays(memberId)));
    }

    @Operation(summary = "출석", description = "이번주 출석 현황을 갱신하고, 오늘 첫 출석 시 50 코인을 지급합니다.")
    @PatchMapping("/attend/{memberId}")
    public ResponseEntity<ApiResponse<AttendDto>> attend(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.attend(memberId)));
    }

    @Operation(summary = "식물 목록 조회", description = "사용자의 식물 목록을 조회합니다.(식물 목록 화면에서 사용)")
    @GetMapping("/plantList/{memberId}")
    public ResponseEntity<ApiResponse<List<PlantListDto>>> getPlantList(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.findPlantList(memberId)));
    }

    @Operation(summary = "식물 등록", description = "식물을 등록합니다.")
    @PostMapping("/plant/{memberId}")
    public ResponseEntity<ApiResponse<PlantResponseDto>> registerPlant(@PathVariable Long memberId, @RequestBody PlantRequestDto plantRequestDto) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.registerPlant(memberId, plantRequestDto.code(), plantRequestDto.name())));
    }

}
