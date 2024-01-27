package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;

import java.util.List;

public interface MatchingScheduleQueryService {
    List<MatchingSchedule> getScheduleList(Branch branch);
}
