package com.example.umcmatchingcenter.converter.matching;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.domain.enums.ScheduleColor;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleResponseDTO;

public class MatchingScheduleConverter {

    public static MatchingScheduleResponseDTO.ScheduleResultDTO toPostScheduleResultDTO(MatchingSchedule schedule) {
        return MatchingScheduleResponseDTO.ScheduleResultDTO.builder()
                .scheduleId(schedule.getId())
                .build();
    }

    public static MatchingSchedule toSchedule(MatchingScheduleRequestDTO.MatchingScheduleDTO request, Branch branch) {

        return MatchingSchedule.builder()
                .branch(branch)
                .scheduleColor(request.getScheduleColor())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .name(request.getTitle())
                .description(request.getDescription())
                .build();
    }
}
