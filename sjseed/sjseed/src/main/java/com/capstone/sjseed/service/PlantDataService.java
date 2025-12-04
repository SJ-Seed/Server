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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantDataService {

    private final PlantDataRepository plantDataRepository;
    private final PlantRepository plantRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;
//    private final WebClient webClient = WebClient.create("https://sj-seed.com"); // TODO: 배포 시 서버 URL로 변경 필수

    @Transactional
    public void save(PlantData plantData){
        PlantData lastData = plantDataRepository.findTopByPlantIdOrderByCreatedAtDesc(plantData.getPlantId());
//        if (lastData == null) {
//   	     log.warn("⚠️ lastData is NULL for plantId: {}", plantData.getPlantId());
//        } else {
//             log.info("✅ lastData found for plantId: {}", lastData.getPlantId());
//             log.info("createdAt: {}", lastData.getCreatedAt());
//        }
        if (lastData != null){
            if (Duration.between(lastData.getCreatedAt(), LocalDateTime.now()).toMinutes() < 60) {
                return;
            }
        }

        plantDataRepository.save(plantData);

        if (!plantRepository.findByPlantId(plantData.getPlantId()).isEmpty()){
            List<Plant> plantList = plantRepository.findByPlantId(plantData.getPlantId());

            for (Plant plant : plantList){
                PlantSpecies plantSpecies = plantSpeciesRepository.findByCode(plantData.getKind())
                        .orElseThrow(() -> new PlantHandler(ErrorStatus.SPECIES_NOT_FOUND)
                        );

                if (plant.getSpecies() == null) {
                    plant.setSpecies(plantSpecies);
                }
                plant.setHumidity(Double.parseDouble(plantData.getHumidity()));
                plant.setTemperature(Double.parseDouble(plantData.getTemperature()));
                plant.setSoilWater(Double.parseDouble(plantData.getSoilWater()));

                plantRepository.save(plant);
            }
        }
    }

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void deleteOldData() {
        plantDataRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(14));
    }

}
