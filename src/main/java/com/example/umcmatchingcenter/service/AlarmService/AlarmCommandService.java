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

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmCommandService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;

    public SseEmitter subscribe(String memberName, String lastEventId) {
        String emitterId = makeTimeIncludeId(memberName);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = makeTimeIncludeId(memberName);
        sendNotification(emitter, eventId, emitterId, "EventStream Created. [memberName=" + memberName + "]");

        if (hasLostData(lastEventId)) {
            sendLostData(lastEventId, memberName, emitterId, emitter);
        }

        return emitter;
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private String makeTimeIncludeId(String memberName) {
        return memberName + "_" + System.currentTimeMillis();
    }

    private void sendLostData(String lastEventId, String memberName, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(memberName));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
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

        String receiverEmail = receiver.getEmail();
        String eventId = receiverEmail + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverEmail);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendNotification(emitter, eventId, key, AlarmConverter.toAlarmDTO(alarm));
                }
        );
    }

}
