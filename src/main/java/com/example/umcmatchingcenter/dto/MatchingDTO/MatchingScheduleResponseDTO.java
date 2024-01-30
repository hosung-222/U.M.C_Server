package com.example.umcmatchingcenter.dto.MatchingDTO;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MatchingScheduleResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleResultDTO {
        private Long scheduleId;
    }

    // 매칭 일정 목록 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingScheduleListDTO {
        private List<MatchingScheduleResponseDTO.MatchingSchedulePreViewDTO> scheduleList;
        private Integer listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingSchedulePreViewDTO {
        private Long scheduleId;
        private String title;
        private String description;
        private String scheduleColor;
        private Integer startYear;
        private Integer startMonth;
        private Integer startDay;
        private Integer endYear;
        private Integer endMonth;
        private Integer endDay;
//        private String startDate;
//        private String endDate;
    }
}
