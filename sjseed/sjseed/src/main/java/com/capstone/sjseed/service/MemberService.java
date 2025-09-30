package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.exception.handler.PlantHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Collection;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.domain.PlantSpecies;
import com.capstone.sjseed.dto.*;
import com.capstone.sjseed.repository.CollectionRepository;
import com.capstone.sjseed.repository.MemberRepository;
import com.capstone.sjseed.repository.PlantRepository;
import com.capstone.sjseed.repository.PlantSpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlantSpeciesRepository plantSpeciesRepository;
    private final PlantRepository plantRepository;

    @Transactional
    public SignupResponseDto signUp(SignupRequestDto signupDto) {
        if (memberRepository.existsByLoginId(signupDto.loginId())) {
            throw new MemberHandler(ErrorStatus.DUPLICATED_ID);
        }

        String encodedPassword = passwordEncoder.encode(signupDto.password());
        Collection collection = Collection.builder().build();

        Member member = Member.builder()
                .name(signupDto.name())
                .year(signupDto.year())
                .loginId(signupDto.loginId())
                .password(encodedPassword)
                .phoneNumber(signupDto.phoneNumber())
                .collection(collection)
                .build();

        memberRepository.save(member);

        return SignupResponseDto.of(
                member.getName(), member.getYear(), member.getLoginId(), member.getPassword(), member.getPhoneNumber()
        );
    }

    @Transactional(readOnly = true)
    public int getCoin(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        return member.getCoin();
    }

    @Transactional(readOnly = true)
    public List<PlantMainDto> getPlantList(Long memberId) { // 메인 화면에서 식물 목록 보기
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        List<Plant> plants = member.getPlants();

        return plants.stream().map(
                plant -> PlantMainDto.of(
                        plant.getName(), plant.getTemperature(), plant.getHumidity(), plant.getSoilWater())
        ).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Boolean[] getAttendedDays(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        return member.getAttendedDays().chars()
                .mapToObj(c -> c == '1')
                .toArray(Boolean[]::new);
    }

    @Transactional
    public AttendDto attend(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        LocalDate today = LocalDate.now();
        int previousCoin = member.getCoin();

        if (!today.equals(member.getLastAttendDate())) {
            member.setLastAttendDate(today);
            member.setCoin(member.getCoin() + 50); // 출석 코인 지급
            int day = LocalDate.now().getDayOfWeek().getValue() - 1;
            if (day == 0) member.initializeAttendedDays(); // 월요일이면 그 주 출석 초기화
            Boolean[] attendedDays = getAttendedDays(memberId);
            attendedDays[LocalDate.now().getDayOfWeek().getValue() - 1] = true; // 오늘 요일 출석으로 변경
            memberRepository.save(member);
        }
        Boolean[] attendedDays = getAttendedDays(memberId);

        return AttendDto.of(attendedDays, member.getCoin() - previousCoin, member.getCoin());
    }

    @Transactional(readOnly = true)
    public List<PlantListDto> findPlantList(Long memberId) { // 식물 목록 보기
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        List<Plant> plants = member.getPlants();

        return plants.stream().map(
                plant -> PlantListDto.of(
                        plant.getName(), plant.getBroughtDate(), plant.isDiseased(), plant.getSpecies().getName()
                )
        ).collect(Collectors.toList());
    }

    @Transactional
    public PlantResponseDto registerPlant(Long memberId, String code, String name) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        Plant plant = Plant.builder()
                .name(name)
                .member(member)
                .build();

        plantRepository.save(plant);

        return PlantResponseDto.of(
                plant.getId(), plant.getName(), plant.getBroughtDate(), plant.getMember().getId()
        );
    }
}
