package com.example.umcmatchingcenter.dto.MatchingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MatchingScheduleResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleResultDTO {
        private Long scheduleId;
    }
}
