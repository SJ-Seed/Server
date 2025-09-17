package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Collection;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.dto.SignupRequestDto;
import com.capstone.sjseed.dto.SignupResponseDto;
import com.capstone.sjseed.repository.CollectionRepository;
import com.capstone.sjseed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public int getCoin(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        return member.getCoin();
    }
}
