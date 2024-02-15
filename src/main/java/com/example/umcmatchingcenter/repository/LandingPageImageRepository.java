package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.mapping.LandingPageImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandingPageImageRepository extends JpaRepository<LandingPageImage, Long> {
    Optional <LandingPageImage> findByImage(Image image);
}
