package com.example.umcmatchingcenter.service.MatchingService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.repository.MatchingScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingScheduleQueryServiceImpl implements MatchingScheduleQueryService {

    private final MatchingScheduleRepository matchingScheduleRepository;

    @Override
    public List<MatchingSchedule> getScheduleList(Branch branch) {
        try {
            return matchingScheduleRepository.findAllByBranchOrderByStartDate(branch);
        } catch (Exception e) {
            throw new RuntimeException("일정 목록을 가져오는 중 오류가 발생했습니다", e);
        }
    }
}
