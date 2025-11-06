package com.capstone.sjseed.service;

import com.capstone.sjseed.apiPayload.exception.handler.MemberHandler;
import com.capstone.sjseed.apiPayload.exception.handler.PieceHandler;
import com.capstone.sjseed.apiPayload.form.status.ErrorStatus;
import com.capstone.sjseed.domain.Member;
import com.capstone.sjseed.domain.Piece;
import com.capstone.sjseed.domain.PlantSpecies;
import com.capstone.sjseed.dto.PieceListDto;
import com.capstone.sjseed.dto.PlantSpeciesDetailDto;
import com.capstone.sjseed.dto.RandomResultDto;
import com.capstone.sjseed.repository.MemberRepository;
import com.capstone.sjseed.repository.PieceRepository;
import com.capstone.sjseed.repository.PlantSpeciesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionService {
    private final MemberRepository memberRepository;
    private final PieceRepository pieceRepository;
    private final PlantSpeciesRepository plantSpeciesRepository;

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

    @Transactional
    public RandomResultDto randomSelect(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND, memberId)
        );

        Random random = new Random();
        int rand = random.nextInt(600);
        String name;

        if (rand < 120) {
            name = null;
        } else if (rand < 200) {
            name = "상추";
        } else if (rand < 280) {
            name = "바질";
        } else if (rand < 360) {
            name = "감자";
        } else if (rand < 410) {
            name = "강낭콩";
        } else if (rand < 460) {
            name = "토마토";
        } else if (rand < 510) {
            name = "딸기";
        } else if (rand < 534) {
            name = "고추";
        } else if (rand < 558) {
            name = "블루베리";
        } else if (rand < 582) {
            name = "체리";
        } else if (rand < 588) {
            name = "라즈베리";
        } else if (rand < 594) {
            name = "포도";
        } else {
            name = "복숭아";
        }

        if (name != null && plantSpeciesRepository.existsByName(name) && !pieceRepository.existsByCollectionAndSpecies(member.getCollection(), plantSpeciesRepository.findByName(name))) {
            PlantSpecies species = plantSpeciesRepository.findByName(name);

            Piece piece = Piece.builder()
                    .species(species)
                    .collection(member.getCollection())
                    .build();

            member.getCollection().addPiece(piece);

            pieceRepository.save(piece);

            return RandomResultDto.of(true, name, piece.getId());
        }

        return RandomResultDto.of(false, null, null);
    }

    @Transactional(readOnly = true)
    public PlantSpeciesDetailDto getPlantSpeciesDetail(Long pieceId) {
        Piece piece = pieceRepository.findById(pieceId).orElseThrow(
                () -> new PieceHandler(ErrorStatus.PIECE_NOT_FOUND, pieceId)
        );

        PlantSpecies species = piece.getSpecies();

        return PlantSpeciesDetailDto.of(species.getName(), species.getProperTemp(), species.getProperHum(), species.getProcess(), species.getWater(), species.getDescription(), species.getRarity());
    }
}
