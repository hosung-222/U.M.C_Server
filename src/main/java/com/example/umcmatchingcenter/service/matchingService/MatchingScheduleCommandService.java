package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface MatchingScheduleCommandService {
    @Transactional
    public abstract MatchingSchedule postSchedule(MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch);

    @Transactional
    public abstract void updateSchedule(Long scheduleId, MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch);

    @Transactional
    public abstract void deleteSchedule(Long scheduleId, Branch branch);
}
