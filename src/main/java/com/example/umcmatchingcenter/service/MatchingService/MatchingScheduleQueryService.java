package com.example.umcmatchingcenter.service.MatchingService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;

import java.util.List;

public interface MatchingScheduleQueryService {
    List<MatchingSchedule> getScheduleList(Branch branch);
}
