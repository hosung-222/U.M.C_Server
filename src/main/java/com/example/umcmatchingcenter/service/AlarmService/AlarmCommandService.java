package com.example.umcmatchingcenter.service.AlarmService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.repository.AlarmRepository;
import com.example.umcmatchingcenter.repository.EmitterRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;

    public SseEmitter subscribe(String memberName) {
        String emitterId = makeEmitterId(memberName);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        String eventId = memberName + "_" + System.currentTimeMillis();
        sendNotification(emitter, eventId, emitterId, "connected [memberName : " + memberName + "]");

        return emitter;
    }

    private String makeEmitterId(String memberName) {
        Member member = memberQueryService.findMemberByName(memberName);

        Branch branch = member.getBranch();


        return memberName + "_" + System.currentTimeMillis() + branch.getName();
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

        String eventId = receiver.getMemberName() + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiver.getMemberName());
        emitters.forEach(
                (key, emitter) -> {
                    sendNotification(emitter, eventId, key, AlarmConverter.toSseAlarmViewDTO(alarm));
                }
        );
    }

    public void sendToBranch(Branch branch, AlarmType alarmType, String content) {
        List<Member> memberList = memberRepository.findByUniversity_Branch(branch);
        List<Alarm> alarmList = alarmRepository.saveAll(AlarmConverter.toAlarmList(memberList, alarmType, content));

        String eventId = branch.getName() + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterEndWithBranchId(branch);
        emitters.forEach(
                (key, emitter) -> {
                    sendNotification(emitter, eventId, key, AlarmConverter.toSseAlarmViewDTO(alarmList.get(0)));
                }
        );
    }

    public int deleteAlarms(String memberName){
        Member member = memberQueryService.findMemberByName(memberName);;

        int deletecout = alarmRepository.deleteAllByIds(member);
        if(deletecout==0){
            throw new AlarmHandler(ErrorStatus.NO_DELETE_ALARM);
        }else{
            return deletecout;
        }
    }
}
