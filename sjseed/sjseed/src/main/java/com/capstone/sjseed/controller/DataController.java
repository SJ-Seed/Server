package com.capstone.sjseed.controller;

import com.capstone.sjseed.domain.PlantData;
import com.capstone.sjseed.service.PlantDataService;
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

    @PostMapping("/get")
    public String getPlantData(@RequestBody PlantData plantData) {
        plantDataService.save(plantData);
        log.info("id={} temp={} hum={} water={}", plantData.getPlantId(), plantData.getTemperature(), plantData.getHumidity(), plantData.getSoilWater());
        return "";
    }
}
