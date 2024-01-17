package com.example.umcmatchingcenter.service.AlarmService;

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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmQueryService {

    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;


    public AlarmResponseDTO.AlarmViewListDTO getAlarmList(String memberName) {

        Member member = memberRepository.findByMemberName(memberName).get();

        List<Alarm> alarmList = alarmRepository.findAllByMember(member);
        updateAlarmIsConfirmed(member);
        return AlarmConverter.toAlarmViewListDTO(alarmList);
    }

    public void updateAlarmIsConfirmed(Member member){
        alarmRepository.updateAlarmIsConfirmed(member);
    }


}
