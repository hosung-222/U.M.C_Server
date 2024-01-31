package com.example.umcmatchingcenter.dto.AlarmDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AlarmResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmViewListDTO{
        private List<AlarmViewDTO> alarmList;
        private Integer listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlarmViewDTO{
        String id;
        String memberName;
        String title;
        String body;
        Boolean isConfirmed;
        String createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteAlarmDTO{
        String memberName;
        int deleteCount;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SseAlarmViewDTO {
        String title;
        String body;
        String createdAt;
    }
}
