package com.example.umcmatchingcenter.service;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ImageHandler;
import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageQueryService {
    private final ImageRepository imageRepository;

    public Image findImageById(Long id){
        return imageRepository.findById(id).orElseThrow(()-> new ImageHandler(ErrorStatus.IMAGE_NOT_EXIST));
    }

    public List<Image> findAllImageById(List<Long> idList){
        return imageRepository.findAllById(idList);
    }
}
