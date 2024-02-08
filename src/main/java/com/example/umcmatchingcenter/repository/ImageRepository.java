package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository  extends JpaRepository<Image, Long> {
}
