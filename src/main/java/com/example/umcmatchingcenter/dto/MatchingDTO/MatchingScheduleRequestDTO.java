package com.example.umcmatchingcenter.dto.MatchingDTO;

import com.example.umcmatchingcenter.domain.enums.ScheduleColor;
import com.sun.istack.NotNull;
import lombok.Getter;

public class MatchingScheduleRequestDTO {
    // 매칭 일정 생성
    @Getter
    public static class MatchingScheduleDTO {

        @NotNull
        String title;

        String description;

        String startDate;

        String endDate;

        ScheduleColor scheduleColor;
    }
}
