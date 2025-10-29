package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Piece;
import com.capstone.sjseed.dto.PieceListDto;
import com.capstone.sjseed.repository.MemberRepository;
import com.capstone.sjseed.repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final MemberRepository memberRepository;
    private final PieceRepository pieceRepository;

    @Transactional(readOnly = true)
    public List<PieceListDto> getPieceList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        List<Piece> pieceList = pieceRepository.findByCollection(member.getCollection());

        return pieceList.stream().map(
                piece -> PieceListDto.of(
                        piece.getSpecies().getName(),
                        piece.getSpecies().getRarity())
        ).collect(Collectors.toList());
    }
}
