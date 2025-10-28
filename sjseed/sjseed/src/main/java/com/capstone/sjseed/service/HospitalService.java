package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.exception.handler.PlantHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Disease;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.domain.Treatment;
import com.capstone.sjseed.dto.TreatmentListDto;
import com.capstone.sjseed.dto.TreatmentRequestDto;
import com.capstone.sjseed.dto.TreatmentResponseDto;
import com.capstone.sjseed.repository.MemberRepository;
import com.capstone.sjseed.repository.PlantRepository;
import com.capstone.sjseed.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final MemberRepository memberRepository;
    private final TreatmentRepository treatmentRepository;
    private final PlantRepository plantRepository;

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
                        treatment.getDisease())
        ).collect(Collectors.toList());
    }

    private final WebClient analyzer = WebClient.builder()
            .baseUrl("https://sj-seed.n-e.kr")
            .build();

    @Transactional
    public TreatmentResponseDto treat(TreatmentRequestDto requestDto, Long memberId, Long plantId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        Plant plant = plantRepository.findById(plantId).orElseThrow(
                () -> new PlantHandler(ErrorStatus.PLANT_NOT_FOUND, plantId)
        );

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
                .plant(plant)
                .build();
        treatmentRepository.save(treatment);

        return dto;
    }
}
