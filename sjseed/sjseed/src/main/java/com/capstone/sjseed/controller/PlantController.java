package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.PlantDetailDto;
import com.capstone.sjseed.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

    @GetMapping("/detail/{plantId}")
    public ResponseEntity<ApiResponse<PlantDetailDto>> getPlantDetail(@PathVariable Long plantId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(plantService.getPlantDetail(plantId)));
    }

    @GetMapping("/ifNeedWater/{plantId}")
    public ResponseEntity<ApiResponse<Boolean>> ifNeedWater(@PathVariable Long plantId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(plantService.ifNeedWater(plantId)));
    }
}
