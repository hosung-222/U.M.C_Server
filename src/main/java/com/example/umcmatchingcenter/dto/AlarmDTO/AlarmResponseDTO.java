package com.example.umcmatchingcenter.dto.AlarmDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponseDTO {
    String memberName;
    String emitterId;
}
