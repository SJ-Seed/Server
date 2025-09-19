package com.capstone.sjseed.service;

import com.capstone.sjseed.domain.PlantData;
import com.capstone.sjseed.repository.PlantDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlantDataService {

    private final PlantDataRepository plantDataRepository;
    private final WebClient webClient = WebClient.create("https://sj-seed.com"); // TODO: 배포 시 서버 URL로 변경 필수

    public void save(PlantData plantData){
        plantDataRepository.save(plantData);
    }

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteOldData() {
        plantDataRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(14));
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void callSaveApiEveryHour() {
        webClient.post()
                .uri("/api/data/get")
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // 동기로 실행 (block 제거하면 비동기 실행됨)
    }
}
