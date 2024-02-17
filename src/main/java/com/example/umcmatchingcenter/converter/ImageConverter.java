package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.LandingPage;
import com.example.umcmatchingcenter.domain.Notice;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.mapping.LandingPageImage;
import com.example.umcmatchingcenter.domain.mapping.NoticeImage;
import com.example.umcmatchingcenter.domain.mapping.ProjectImage;
import com.example.umcmatchingcenter.dto.ImageDTO;

public class ImageConverter {

    public static Image toImage(String originalFilename, String s3ImageUrl){
        return Image.builder()
                .originalFilename(originalFilename)
                .s3ImageUrl(s3ImageUrl)
                .build();
    }

    public static ProjectImage toProjectImage(Project project, Image image){
        return ProjectImage.builder()
                .project(project)
                .image(image)
                .build();
    }

    public static LandingPageImage toLandingPageImage(LandingPage landingPage, Image image){
        return LandingPageImage.builder()
                .landingPage(landingPage)
                .image(image)
                .build();
    }

    public static ImageDTO.UploadImageResponseDTO toUploadImageResponseDTO(Image image){
        return ImageDTO.UploadImageResponseDTO.builder()
                .imageId(image.getId())
                .s3Image(image.getS3ImageUrl())
                .build();
    }

    public static NoticeImage toNoticeImage(Image image, Notice notice){
        return NoticeImage.builder()
                .image(image)
                .notice(notice)
                .build();
    }
}
