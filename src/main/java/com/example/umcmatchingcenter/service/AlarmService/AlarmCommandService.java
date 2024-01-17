package com.example.umcmatchingcenter.service.AlarmService;

import com.example.umcmatchingcenter.apiPayload.ApiResponse;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.converter.MemberConverter;
import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import com.example.umcmatchingcenter.repository.AlarmRepository;
import com.example.umcmatchingcenter.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmCommandService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;

    public SseEmitter subscribe(String memberName) {
        String emitterId = makeTimeIncludeId(memberName);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(memberName);
        sendNotification(emitter, eventId, emitterId, "연결 성공" + memberName + "]");

        return emitter;
    }

    private String makeTimeIncludeId(String memberName) {
        return memberName + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    public void send(Member receiver, AlarmType alarmType, String content, String url) {
        Alarm alarm = alarmRepository.save(AlarmConverter.toAlarm(receiver, alarmType, content, url));

        String memberName = receiver.getMemberName();
        String eventId = memberName + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(memberName);
        emitters.forEach(
                (key, emitter) -> {
                    sendNotification(emitter, eventId, key, AlarmConverter.toAlarmViewDTO(alarm));
                }
        );
    }
}
