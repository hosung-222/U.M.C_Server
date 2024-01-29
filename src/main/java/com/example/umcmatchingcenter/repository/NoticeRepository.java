package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
