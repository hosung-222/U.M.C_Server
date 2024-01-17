package com.example.umcmatchingcenter.dto.AlarmDTO;

import com.example.umcmatchingcenter.domain.enums.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
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
        String body;
        Boolean isConfirmed;
        String createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteAlarmDTO{
        String id;
        String memberName;
        String body;
        Boolean isConfirmed;
        String createdAt;
    }

}
