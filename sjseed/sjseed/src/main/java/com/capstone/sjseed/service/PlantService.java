package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.PlantHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.domain.PlantData;
import com.capstone.sjseed.domain.PlantSpecies;
import com.capstone.sjseed.dto.PlantDetailDto;
import com.capstone.sjseed.repository.PlantDataRepository;
import com.capstone.sjseed.repository.PlantRepository;
import com.capstone.sjseed.repository.PlantSpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final PlantSpeciesRepository  plantSpeciesRepository;
    private final PlantDataRepository plantDataRepository;

    @Transactional(readOnly = true)
    public PlantDetailDto getPlantDetail(Long plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(
                () -> new PlantHandler(ErrorStatus.PLANT_NOT_FOUND, plantId)
        );

        PlantSpecies species = plantSpeciesRepository.findById(plant.getSpecies().getId()).orElseThrow(
                () -> new PlantHandler(ErrorStatus.SPECIES_NOT_FOUND, plant.getSpecies().getId())
        );

        return PlantDetailDto.of(
                plant.getName(), species.getName(), plant.getBroughtDate(), species.getDescription(), species.getProperTemp(),
                species.getProperHum(), species.getWater());
    }

    @Transactional(readOnly = true)
    public boolean ifNeedWater(Long plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(
                () -> new PlantHandler(ErrorStatus.PLANT_NOT_FOUND, plantId)
        );

        PlantSpecies species = plantSpeciesRepository.findById(plant.getSpecies().getId()).orElseThrow(
                () -> new PlantHandler(ErrorStatus.SPECIES_NOT_FOUND, plant.getSpecies().getId())
        );

        long daySinceWatered;

        if (plant.getWateredDate() != null) {
            daySinceWatered = ChronoUnit.DAYS.between(plant.getWateredDate(), LocalDate.now());
        } else {
            return true;
        }

        return daySinceWatered >= species.getPeriod();
    }

    @Transactional
    public boolean ifWatered(Long plantId) {
        Plant plant = plantRepository.findById(plantId).orElseThrow(
                () -> new PlantHandler(ErrorStatus.PLANT_NOT_FOUND, plantId)
        );

        PlantData data = plantDataRepository.findTopByPlantIdOrderByCreatedAtDesc(plant.getPlantId());
        plant.setWateredDate(LocalDate.now());
        return Integer.parseInt(data.getSoilWater()) <= 450;
    }
}
