package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.PlantHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.domain.PlantData;
import com.capstone.sjseed.domain.PlantSpecies;
import com.capstone.sjseed.repository.PlantDataRepository;
import com.capstone.sjseed.repository.PlantRepository;
import com.capstone.sjseed.repository.PlantSpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlantDataService {

    private final PlantDataRepository plantDataRepository;
    private final PlantRepository plantRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;
//    private final WebClient webClient = WebClient.create("https://sj-seed.com"); // TODO: 배포 시 서버 URL로 변경 필수

    public void save(PlantData plantData){
        plantDataRepository.save(plantData);

        Plant plant = plantRepository.findByPlantId(plantData.getPlantId())
                .orElseThrow(() -> new PlantHandler(ErrorStatus.PLANT_NOT_FOUND)
                );

        PlantSpecies plantSpecies = plantSpeciesRepository.findByCode(plantData.getKind())
                .orElseThrow(() -> new PlantHandler(ErrorStatus.SPECIES_NOT_FOUND)
                );

        if (plant.getSpecies() == null) {
            plant.setSpecies(plantSpecies);
            plantRepository.save(plant);
        }
    }

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteOldData() {
        plantDataRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(14));
    }

//    @Scheduled(cron = "0 0 * * * ?")
//    public void callSaveApiEveryHour() {
//        webClient.post()
//                .uri("/api/data/get")
//                .retrieve()
//                .bodyToMono(Void.class)
//                .block(); // 동기로 실행 (block 제거하면 비동기 실행됨)
//    }
}
