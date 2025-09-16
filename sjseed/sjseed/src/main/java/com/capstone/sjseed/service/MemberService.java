package com.capstone.sjseed.service;

import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.apiPayload.exception.DuplicateLoginException;
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
    public Long signUp(String name, int year, String loginId, String password, String phoneNumber) {
        if (memberRepository.existsByLoginId(loginId)) {
            throw new DuplicateLoginException(loginId);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .name(name)
                .year(year)
                .loginId(loginId)
                .password(encodedPassword)
                .phoneNumber(phoneNumber)
                .build();

        return memberRepository.save(member).getId();
    }
}
