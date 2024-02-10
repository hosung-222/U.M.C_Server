package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.dto.ImageDTO;

public class ImageConverter {

    public static Image toImage(String originalFilename, String s3Filename){
        return Image.builder()
                .originalImage(originalFilename)
                .s3Image(s3Filename)
                .build();
    }

    public static ImageDTO.UploadImageResponseDTO toUploadImageResponseDTO(Image image){
        return ImageDTO.UploadImageResponseDTO.builder()
                .imageId(image.getId())
                .s3Image(image.getS3Image())
                .build();
    }
}
