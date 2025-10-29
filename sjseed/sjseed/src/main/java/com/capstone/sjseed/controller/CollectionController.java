package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.PieceListDto;
import com.capstone.sjseed.dto.RandomResultDto;
import com.capstone.sjseed.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @Operation(summary = "사용자의 도감 조각 목록 조회", description = "사용자가 얻은 조각 목록을 조회합니다.")
    @GetMapping("/pieceList/{memberId}")
    public ResponseEntity<ApiResponse<List<PieceListDto>>> getPieceList(@PathVariable Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess(collectionService.getPieceList(memberId)));
    }

    @Operation(summary = "도감 조각 뽑기", description = "도감 조각을 랜덤으로 뽑습니다.")
    @GetMapping("/random")
    public ResponseEntity<ApiResponse<RandomResultDto>> getRandomResult() {
        return ResponseEntity.ok(ApiResponse.onSuccess(collectionService.randomSelect()));
    }
}
