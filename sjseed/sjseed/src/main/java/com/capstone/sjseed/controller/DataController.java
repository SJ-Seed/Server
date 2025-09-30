package com.capstone.sjseed.controller;

import com.capstone.sjseed.domain.PlantData;
import com.capstone.sjseed.service.PlantDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {

    private final PlantDataService plantDataService;

    @Operation(summary = "아두이노로부터 얻은 식물 데이터 저장", description = "아두이노로부터 얻은 식물의 온습도, 토양 수분 정보를 저장합니다.")
    @PostMapping("/get")
    public String getPlantData(@RequestBody PlantData plantData) {
        plantDataService.save(plantData);
        log.info("id={} kind={} temp={} hum={} water={}", plantData.getPlantId(), plantData.getKind(), plantData.getTemperature(), plantData.getHumidity(), plantData.getSoilWater());
        return "";
    }
}
