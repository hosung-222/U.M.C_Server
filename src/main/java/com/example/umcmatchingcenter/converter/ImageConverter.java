package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Image;

public class ImageConverter {

    public static Image toImage(String originalFilename, String s3Filename){
        return Image.builder()
                .originalImage(originalFilename)
                .s3Image(s3Filename)
                .build();
    }
}
