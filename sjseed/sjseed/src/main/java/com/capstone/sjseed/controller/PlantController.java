package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.PlantDetailDto;
import com.capstone.sjseed.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

    @Operation(summary = "식물 상세 정보 조회", description = "자신이 기르고 있는 식물의 상세 정보를 조회합니다.")
    @GetMapping("/detail/{plantId}")
    public ResponseEntity<ApiResponse<PlantDetailDto>> getPlantDetail(@PathVariable Long plantId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(plantService.getPlantDetail(plantId)));
    }

    @Operation(summary = "물 주기 필요 여부 조회", description = "식물에 물을 줘야 하는 주기가 되었는지를 반환합니다.")
    @GetMapping("/ifNeedWater/{plantId}")
    public ResponseEntity<ApiResponse<Boolean>> ifNeedWater(@PathVariable Long plantId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(plantService.ifNeedWater(plantId)));
    }

    @Operation(summary = "물 주기 여부 확인", description = "식물에 물을 줬는지 확인합니다.")
    @GetMapping("/ifWatered/{plantId}")
    public ResponseEntity<ApiResponse<Boolean>> ifWatered(@PathVariable Long plantId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(plantService.ifWatered(plantId)));
    }
}
