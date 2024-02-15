package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.mapping.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    Optional<ProjectImage> findByImage(Image image);
}
