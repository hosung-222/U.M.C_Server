package com.example.umcmatchingcenter.service.AlarmService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import com.example.umcmatchingcenter.repository.AlarmRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmQueryService {

    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;


    public AlarmResponseDTO.AlarmViewListDTO getAlarmList(String memberName) {

        Member member = memberRepository.findByMemberName(memberName).get();

        List<Alarm> alarmList = alarmRepository.findAllByMember(member);

        if (alarmList == null || alarmList.isEmpty())
            throw new AlarmHandler(ErrorStatus.NO_ALARM_LIST);

        updateAlarmIsConfirmed(member);
        return AlarmConverter.toAlarmViewListDTO(alarmList);
    }

    public void updateAlarmIsConfirmed(Member member){
        alarmRepository.updateAlarmIsConfirmed(member);
    }


}
