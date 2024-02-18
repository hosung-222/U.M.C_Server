package com.example.umcmatchingcenter.dto.AlarmDTO;

import com.example.umcmatchingcenter.domain.enums.AlarmType;
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
        private Long id;
        private AlarmType alarmType        ;
        private String body;
        private Boolean isConfirmed;
        private String createdAt;
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
