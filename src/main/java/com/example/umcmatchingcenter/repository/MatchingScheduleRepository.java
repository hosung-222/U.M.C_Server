package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.MatchingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingScheduleRepository extends JpaRepository<MatchingSchedule, Long> {
    MatchingSchedule findScheduleById(Long id);
}
