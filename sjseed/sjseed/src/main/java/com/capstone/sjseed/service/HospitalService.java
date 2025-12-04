package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.exception.handler.PlantHandler;
import com.capstone.sjseed.apiPayload.exception.handler.TreatmentHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.*;
import com.capstone.sjseed.dto.*;
import com.capstone.sjseed.repository.MemberRepository;
import com.capstone.sjseed.repository.PlantDataRepository;
import com.capstone.sjseed.repository.PlantRepository;
import com.capstone.sjseed.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final MemberRepository memberRepository;
    private final TreatmentRepository treatmentRepository;
    private final PlantRepository plantRepository;
    private final PlantDataRepository plantDataRepository;

    @Transactional(readOnly = true)
    public List<TreatmentListDto> findTreatmentList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );
        List<Treatment> treatments = treatmentRepository.findByMember(member);

        return treatments.stream().map(
                treatment -> TreatmentListDto.of(
                        treatment.getPlant().getName(),
                        treatment.getDate(),
                        treatment.getDisease(),
                        treatment.getPlant().getId(),
                        treatment.getPlant().getSpecies().getId(),
                        treatment.getId())
        ).collect(Collectors.toList());
    }

    private final WebClient analyzer = WebClient.builder()
            .baseUrl("https://sj-seed.n-e.kr")
            .build();

    private PlantStatusDto getRecentTwoWeeksData(Long plantId) {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        List<PlantData> recentData = plantDataRepository.findRecentTwoWeeks(String.valueOf(plantId), twoWeeksAgo);

        if (recentData.isEmpty()) {
            return PlantStatusDto.of(Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
        }

        double avgTemp = recentData.stream()
                .mapToDouble(p -> Double.parseDouble(p.getTemperature()))
                .average()
                .orElse(Double.NaN);

        double maxTemp = recentData.stream()
                .mapToDouble(p -> Double.parseDouble(p.getTemperature()))
                .max()
                .orElse(Double.NaN);

        double minTemp = recentData.stream()
                .mapToDouble(p -> Double.parseDouble(p.getTemperature()))
                .min()
                .orElse(Double.NaN);

        double avgHumidity = recentData.stream()
                .mapToDouble(p -> Double.parseDouble(p.getHumidity()))
                .average()
                .orElse(Double.NaN);

        double maxHumidity = recentData.stream()
                .mapToDouble(p -> Double.parseDouble(p.getHumidity()))
                .max()
                .orElse(Double.NaN);

        double minHumidity = recentData.stream()
                .mapToDouble(p -> Double.parseDouble(p.getHumidity()))
                .min()
                .orElse(Double.NaN);

        PlantData latest = plantDataRepository.findTopByPlantIdOrderByCreatedAtDesc(String.valueOf(plantId));
        double latestTemp = latest != null ? Double.parseDouble(latest.getTemperature()) : Double.NaN;
        double latestHumidity = latest != null ? Double.parseDouble(latest.getHumidity()) : Double.NaN;

        return PlantStatusDto.of(avgTemp, avgHumidity, maxTemp, minTemp, maxHumidity, minHumidity, latestTemp, latestHumidity);
    }


    @Transactional
    public TreatmentResponseDto treat(ImageUrlDto imageUrlDto, Long memberId, Long plantId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        Plant plant = plantRepository.findById(plantId).orElseThrow(
                () -> new PlantHandler(ErrorStatus.PLANT_NOT_FOUND, plantId)
        );

        PlantStatusDto status = getRecentTwoWeeksData(plantId);
        // "최고 기온: 32도, 최저 기온: 25도, 최근 기온: 28도, 평균 기온: 28도"
        String temp = "최고 기온: " + status.maxTemp() + "도, 최저 기온: " + status.minTemp() + "도, 최근 기온: " + status.lastTemp() + "도, 평균 기온: " + status.avgTemp() + "도";
        // "최고 습도: 85%, 최저 습도: 75%, 최근 습도: 85%, 평균 습도: 80%"
        String hum = "최고 습도: " + status.maxHum() + "%, 최저 습도: " + status.minHum() + "%, 최근 습도: " + status.lastHum() + "%, 평균 습도: " + status.avgHum() + "%";

        TreatmentRequestDto requestDto = TreatmentRequestDto.of(imageUrlDto.url(), temp, hum);

        TreatmentResponseDto dto = analyzer.post()
                .uri("/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TreatmentResponseDto.class)
                .block(); // 동기 처리 (필요시 비동기로도 가능)

        Treatment treatment = Treatment.builder()
                .member(member)
                .disease(dto.state())
                .explain(dto.explain())
                .cause(dto.cause())
                .cure(dto.cure())
                .imageUrl(imageUrlDto.url())
                .plant(plant)
                .build();
        treatmentRepository.save(treatment);

        if (dto.state() == null) {
            Optional<Treatment> lastTreatment = treatmentRepository.findTopByMemberAndPlantOrderByDateDesc(member, plant);
            if (lastTreatment.isPresent()) {
                if (lastTreatment.get().getDisease() != null) {
                    member.setCoin(member.getCoin() + 100);
                }
            }
        }

        return dto;
    }

    @Transactional(readOnly = true)
    public TreatmentDetailDto getTreatmentDetail(Long treatmentId) {
        Treatment treatment = treatmentRepository.findById(treatmentId).orElseThrow(
                () -> new TreatmentHandler(ErrorStatus.TREATMENT_NOT_FOUND, treatmentId)
        );

        return TreatmentDetailDto.of(treatment.getPlant().getId(), treatment.getPlant().getSpecies().getId(), treatment.getDisease(), treatment.getSymptoms(), treatment.getCause(), treatment.getCure(), treatment.getImageUrl());
    }
}
