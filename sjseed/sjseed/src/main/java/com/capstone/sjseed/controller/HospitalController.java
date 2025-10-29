package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.TreatmentListDto;
import com.capstone.sjseed.dto.TreatmentRequestDto;
import com.capstone.sjseed.dto.TreatmentResponseDto;
import com.capstone.sjseed.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @Operation(summary = "진료 기록 조회", description = "사용자의 진료 기록을 모두 조회합니다.")
    @GetMapping("/treatmentList/{memberId}")
    public ResponseEntity<ApiResponse<List<TreatmentListDto>>> getTreatmentList(@PathVariable Long memberId){
        return ResponseEntity.ok(ApiResponse.onSuccess(hospitalService.findTreatmentList(memberId)));
    }

    @Operation(summary = "진료 보기", description = "LLM API를 호출하여 식물 사진의 질병 유무를 확인합니다.")
    @PostMapping("/treat/{memberId}/{plantId}")
    public ResponseEntity<ApiResponse<TreatmentResponseDto>> analyze(@RequestBody String url , @PathVariable Long memberId, @PathVariable Long plantId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(hospitalService.treat(url, memberId, plantId)));
    }
}
