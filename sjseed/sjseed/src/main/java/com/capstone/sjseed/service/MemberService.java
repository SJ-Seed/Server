package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Collection;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.dto.PlantMainDto;
import com.capstone.sjseed.dto.SignupRequestDto;
import com.capstone.sjseed.dto.SignupResponseDto;
import com.capstone.sjseed.repository.CollectionRepository;
import com.capstone.sjseed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
    public List<PlantMainDto> getPlantList(Long memberId) {
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
}
