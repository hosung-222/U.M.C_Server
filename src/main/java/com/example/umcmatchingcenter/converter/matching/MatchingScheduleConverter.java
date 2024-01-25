package com.example.umcmatchingcenter.converter.matching;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.domain.enums.ScheduleColor;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MatchingScheduleConverter {
    // 매칭 일정 생성
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

    // 매칭 일정 전체 조회
    public static MatchingScheduleResponseDTO.MatchingSchedulePreViewDTO toSchedulePreViewDTO(MatchingSchedule schedule) {
        return MatchingScheduleResponseDTO.MatchingSchedulePreViewDTO.builder()
                .scheduleId(schedule.getId())
                .title(schedule.getName())
                .description(schedule.getDescription())
                .scheduleColor(schedule.getScheduleColor().getColor())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .build();
    }

    public static MatchingScheduleResponseDTO.MatchingScheduleListDTO toSchedulePreViewListDTO(List<MatchingSchedule> scheduleList) {

        List<MatchingScheduleResponseDTO.MatchingSchedulePreViewDTO> schedulePreViewDTOList = scheduleList.stream()
                .map(MatchingScheduleConverter::toSchedulePreViewDTO).collect(Collectors.toList());

        return MatchingScheduleResponseDTO.MatchingScheduleListDTO.builder()
                .listSize(schedulePreViewDTOList.size())
                .scheduleList(schedulePreViewDTOList)
                .build();
    }
}
