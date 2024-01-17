package com.example.umcmatchingcenter.service.AlarmService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.repository.AlarmRepository;
import com.example.umcmatchingcenter.repository.EmitterRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public SseEmitter subscribe(String memberName) {
        String emitterId = makeTimeIncludeId(memberName);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = makeTimeIncludeId(memberName);
        sendNotification(emitter, eventId, emitterId, "연결 성공 [멤버 닉네임 : " + memberName + "]");

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

    public void send(Member receiver, AlarmType alarmType, String content) {
        Alarm alarm = alarmRepository.save(AlarmConverter.toAlarm(receiver, alarmType, content));

        String memberName = receiver.getMemberName();
        String eventId = memberName + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(memberName);
        emitters.forEach(
                (key, emitter) -> {
                    sendNotification(emitter, eventId, key, AlarmConverter.toSseAlarmViewDTO(alarm));
                }
        );
    }

    public int deleteAlarms(String memberName){
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(()-> new AlarmHandler(ErrorStatus.MEMBER_NOT_FOUND));

        int deletecout = alarmRepository.deleteAllByIds(member);
        if(deletecout==0){
            throw new AlarmHandler(ErrorStatus.NO_DELETE_ALARM);
        }else{
            return deletecout;
        }
    }
}
