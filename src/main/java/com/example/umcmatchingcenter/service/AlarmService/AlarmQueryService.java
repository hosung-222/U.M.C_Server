package com.example.umcmatchingcenter.service.AlarmService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.converter.AlarmConverter;
import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;
import com.example.umcmatchingcenter.repository.AlarmRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmQueryService {

    private final MemberQueryService memberQueryService;
    private final AlarmRepository alarmRepository;


    public AlarmResponseDTO.AlarmViewListDTO getAlarmList(String memberName) {

        Member member = memberQueryService.findMemberByName(memberName);

        List<Alarm> alarmList = alarmRepository.findAllByMember(member);

        return AlarmConverter.toAlarmViewListDTO(alarmList);
    }

}
