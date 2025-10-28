package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Treatment;
import com.capstone.sjseed.dto.TreatmentListDto;
import com.capstone.sjseed.repository.MemberRepository;
import com.capstone.sjseed.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final MemberRepository memberRepository;
    private final TreatmentRepository treatmentRepository;

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
                        treatment.getDisease().getName())
        ).collect(Collectors.toList());
    }
}
