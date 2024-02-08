package com.example.umcmatchingcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ImageDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadImageResponseDTO {
        private Long imageId;
        private String s3Image;
    }
}
