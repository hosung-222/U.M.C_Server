package com.example.umcmatchingcenter.service.s3Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.NoticeHandler;
import com.example.umcmatchingcenter.converter.ImageConverter;
import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.Uuid;
import com.example.umcmatchingcenter.repository.ImageRepository;
import com.example.umcmatchingcenter.service.UuidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3 amazonS3;
    private final UuidService uuidService;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String s3Filename = uploadFile(file);
        Image image = ImageConverter.toImage(originalFilename, s3Filename);

        imageRepository.save(image);

        return null;
    }

    public void delete(String path) {
        amazonS3.deleteObject(bucket, path);
    }

    public String uploadFile(MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();

        String originalFilename = file.getOriginalFilename();
        Uuid uuid = uuidService.makeUuid();
        String newName = uuid.getUuid() + "/" + originalFilename;

        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(bucket, newName, file.getInputStream(), metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
            throw new MemberHandler(ErrorStatus.MEMBER_PROFILE_ERROR);
        }

        return amazonS3.getUrl(bucket, newName).toString();
    }

    public List<String> uploadFileList(List<MultipartFile> fileList){
        List<String> urlList = new ArrayList<>();
        for(MultipartFile file : fileList){
            urlList.add(uploadFile(file));
        }

        return urlList;
    }

}