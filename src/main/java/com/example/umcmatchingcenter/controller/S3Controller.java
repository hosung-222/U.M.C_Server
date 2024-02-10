package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.ImageConverter;
import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.dto.ImageDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3UploadService s3UploadService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<ImageDTO.UploadImageResponseDTO> upload(@RequestPart MultipartFile file) throws IOException {
        Image image = s3UploadService.saveFile(file);
        return ApiResponse.onSuccess(ImageConverter.toUploadImageResponseDTO(image));
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> fileDelete(@RequestParam("path") String path) {
        s3UploadService.delete(path);
        return ApiResponse.onSuccess("삭제 성공");
    }

}
