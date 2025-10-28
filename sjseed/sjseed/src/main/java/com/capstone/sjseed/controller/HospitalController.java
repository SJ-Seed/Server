package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.TreatmentListDto;
import com.capstone.sjseed.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
