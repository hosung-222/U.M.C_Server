package com.example.umcmatchingcenter.service.AlarmService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.repository.AlarmRepository;
import com.example.umcmatchingcenter.repository.EmitterRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.UniversityRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmCommandService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;
    private final UniversityRepository universityRepository;
    /*

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
     */

    public void sendToBranch(Branch branch, AlarmType alarmType, String content) {
        List<University> universities = branch.getUniversities();
        List<Member> branchMemberList = new ArrayList<>();

        for (University university : universities) {
            List<Member> universityMemberList = university.getMembers();
            branchMemberList.addAll(universityMemberList);
        }
        alarmRepository.saveAll(AlarmConverter.toAlarmList(branchMemberList, alarmType, content));

    }

    public void send(Member receiver, AlarmType alarmType, String content) {
        Alarm alarm = alarmRepository.save(AlarmConverter.toAlarm(receiver, alarmType, content));

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
