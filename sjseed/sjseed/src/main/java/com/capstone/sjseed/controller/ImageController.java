package com.capstone.sjseed.controller;

import com.capstone.sjseed.apiPayload.ApiResponse;
import com.capstone.sjseed.dto.ImageDto;
import com.capstone.sjseed.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "사진 업로드", description = "AWS S3에 사진을 업로드하고 그 링크를 반환합니다.")
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<ImageDto>> uploadImage(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(ApiResponse.onSuccess(imageService.saveImage(file)));
    }
}
