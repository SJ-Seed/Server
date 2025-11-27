package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.exception.handler.PlantHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.config.JwtProvider;
import com.capstone.sjseed.domain.Collection;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Plant;
import com.capstone.sjseed.dto.*;
import com.capstone.sjseed.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CollectionRepository collectionRepository;
    private final PlantRepository plantRepository;
    private final PieceRepository pieceRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signupDto) {
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

        collection.setMember(member);
        collectionRepository.save(collection);
        memberRepository.save(member);

        return SignUpResponseDto.of(
                member.getName(), member.getYear(), member.getLoginId(), member.getPassword(), member.getPhoneNumber()
        );
    }

    @Transactional
    public SignInResponseDto signIn(SignInRequestDto requestDto) {
        Member member = memberRepository.findByLoginId((requestDto.loginId()))
                .orElseThrow(() -> new MemberHandler(ErrorStatus.INVALID_ID_OR_PASSWORD));

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword())) {
            throw new MemberHandler(ErrorStatus.INVALID_ID_OR_PASSWORD);
        }

        String token = jwtProvider.createToken(member.getLoginId());

        return SignInResponseDto.of(String.valueOf(member.getId()), member.getName(), member.getLoginId(), token);
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
                        plant.getId(), plant.getName(), plant.getTemperature(), plant.getHumidity(), plant.getSoilWater(), plant.getSpecies().getId())
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

        int index = today.getDayOfWeek().getValue() - 1;
        boolean ifFirstAttend = false;

        for (int i = index + 1; i < member.getAttendedDays().length(); i++) {
            if (member.getAttendedDays().charAt(i) == '1') {
                ifFirstAttend = true;
                break;
            }
        }

        // 월요일이면 연속 출석 초기화
        if (today.getDayOfWeek() == DayOfWeek.MONDAY || ifFirstAttend) {
            member.setConsecutiveAttendDays(0);
        }

        if (!today.equals(member.getLastAttendDate()) || member.getAttendedDays().charAt(index) == '0') {
            // 마지막 출석일과 오늘 날짜 차이 확인
            if (member.getLastAttendDate() != null && member.getLastAttendDate().plusDays(1).equals(today)) {
                // 어제도 출석했으면 연속 출석 +1
                member.setConsecutiveAttendDays(member.getConsecutiveAttendDays() + 1);
            } else {
                // 아니면 1일차로 초기화
                member.setConsecutiveAttendDays(1);
            }

            member.setLastAttendDate(today);
            member.attendDay(LocalDate.now().getDayOfWeek().getValue() - 1);

            // 코인 보상 테이블
            int reward = getRewardByStreak(member.getConsecutiveAttendDays());
            member.setCoin(member.getCoin() + reward);

            memberRepository.save(member);

            return AttendDto.of(getAttendedDays(memberId), reward, member.getCoin());
        }

        // 이미 오늘 출석했다면 보상 없음
        return AttendDto.of(getAttendedDays(memberId), 0, member.getCoin());
    }

    private int getRewardByStreak(int streak) {
        return switch (streak) {
            case 1 -> 10;
            case 2 -> 30;
            case 3 -> 50;
            case 4 -> 70;
            case 5 -> 100;
            case 6 -> 100;
            case 7 -> 150;
            default -> 0;
        };
    }

    @Transactional(readOnly = true)
    public List<PlantListDto> findPlantList(Long memberId) { // 식물 목록 보기
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        List<Plant> plants = member.getPlants();

        return plants.stream().map(
                plant -> PlantListDto.of(
                        plant.getId(), plant.getName(), plant.getBroughtDate(), plant.isDiseased(), plant.getSpecies().getName(), plant.getSpecies().getId()
                )
        ).collect(Collectors.toList());
    }

    @Transactional
    public PlantResponseDto registerPlant(Long memberId, String code, String name) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        if (plantRepository.findByName(name).isPresent()) {
            if (plantRepository.findByName(name).get().getMember().getId().equals(member.getId())) {
                throw new PlantHandler(ErrorStatus.DUPLICATED_NAME, name);
            }
        }

        Plant plant = Plant.builder()
                .name(name)
                .member(member)
                .plantId(code)
                .build();

        return PlantResponseDto.of(
                plant.getId(), plant.getName(), plant.getBroughtDate(), plant.getMember().getId()
        );
    }

    @Transactional(readOnly = true)
    public MemberDetailDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        long plantNum = plantRepository.countByMember(member);
        long pieceNum = pieceRepository.countByMemberId(memberId);

        return MemberDetailDto.of(member.getName(), (int) plantNum, (int) pieceNum);
    }

    @Transactional
    public MemberDto updatePhoneNumber(Long memberId, String phoneNumber) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        member.setPhoneNumber(phoneNumber);
        memberRepository.save(member);

        return MemberDto.of(member.getId(), member.getName(), member.getLoginId(), member.getPhoneNumber());
    }
}
