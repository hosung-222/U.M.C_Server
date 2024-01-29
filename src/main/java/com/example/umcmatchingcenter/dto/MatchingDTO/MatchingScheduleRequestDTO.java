package com.example.umcmatchingcenter.dto.MatchingDTO;

import com.example.umcmatchingcenter.domain.enums.ScheduleColor;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class MatchingScheduleRequestDTO {
    // 매칭 일정 생성
    @Getter
    public static class MatchingScheduleDTO {

        @NotNull
        @Schema(description =  "일정 이름", example = "일정 이름")
        String title;

        @Schema(description =  "일정 메모", example = "일정 메모 (선택)")
        String description;

        @Schema(description = "시작 연도", example = "24")
        Integer startYear;

        @Schema(description = "시작 월", example = "01")
        Integer startMonth;

        @Schema(description = "시작일", example = "31")
        Integer startDay;

        @Schema(description = "종료 연도", example = "24")
        Integer endYear;

        @Schema(description = "종료 월", example = "02")
        Integer endMonth;

        @Schema(description = "종료일", example = "01")
        Integer endDay;



//        @Schema(description = "시작일", example = "2024-01-25")
//        String startDate;
//
//        @Schema(description = "종료일", example = "2024-02-01")
//        String endDate;

        @Schema(description = "일정 색상", example = "일정 색상 (WHITE, RED, ORANGE, ..)")
        ScheduleColor scheduleColor;
    }
}
