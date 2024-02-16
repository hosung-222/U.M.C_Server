package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.mapping.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeImageRepository extends JpaRepository<NoticeImage, Long> {
    Optional<NoticeImage> findByImage(Image image);
}
