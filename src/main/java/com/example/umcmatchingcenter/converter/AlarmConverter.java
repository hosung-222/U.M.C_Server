package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AlarmConverter {
    public static Alarm toAlarm(Member receiver, AlarmType alarmType, String content, String url){

        return Alarm.builder()
                .member(receiver)
                .alarmType(alarmType)
                .body(content)
                .url(url)
                .title("제목")
                .build();
    }

    public static AlarmResponseDTO.AlarmViewDTO toAlarmViewDTO(Alarm alarm){
        return AlarmResponseDTO.AlarmViewDTO.builder()
                .memberName(alarm.getMember().getMemberName())
                .id(alarm.getId().toString())
                .body(alarm.getBody())
                .isConfirmed(alarm.getIsConfirmed())
                .createdAt(alarm.getCreatedAt().toString())
                .build();
    }

    public static AlarmResponseDTO.AlarmViewListDTO toAlarmViewListDTO(List<Alarm> alarmList){
        List<AlarmResponseDTO.AlarmViewDTO> alarmViewDTOList = alarmList.stream()
                .map(AlarmConverter::toAlarmViewDTO).collect(Collectors.toList());

        return AlarmResponseDTO.AlarmViewListDTO.builder()
                .alarmList(alarmViewDTOList)
                .listSize(alarmViewDTOList.size())
                .build();
    }
}
