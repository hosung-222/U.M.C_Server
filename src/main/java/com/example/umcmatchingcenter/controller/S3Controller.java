package com.example.umcmatchingcenter.controller;

import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) throws IOException {
        s3UploadService.saveFile(file);
        return ResponseEntity.ok("File upload successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> fileDelete(@RequestParam("path") String path) {
        s3UploadService.delete(path);
        return ResponseEntity.ok("File deleted successfully");
    }

}
