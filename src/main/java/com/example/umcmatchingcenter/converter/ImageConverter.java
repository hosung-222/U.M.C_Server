package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Image;

public class ImageConverter {

    public static Image toImage(String originalFilename, String s3Filename){
        return Image.builder()
                .imageUrl(originalFilename)
                .s3ImageUrl(s3Filename)
                .build();
    }
}
