package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingScheduleRepository extends JpaRepository<MatchingSchedule, Long> {
    MatchingSchedule findScheduleById(Long id);

    List<MatchingSchedule> findAllByBranchOrderByStartDate(Branch branch);
}
