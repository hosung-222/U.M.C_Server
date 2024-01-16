package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmDTO;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;

public class AlarmConverter {
    public static Alarm toAlarm(Member receiver, AlarmType alarmType, String content, String url){

        return Alarm.builder()
                .member(receiver)
                .alarmType(alarmType)
                .body(content)
                .url(url)
                .build();
    }

    public static AlarmDTO toAlarmDTO(Alarm alarm){
        return AlarmDTO.builder()
                .memberName(alarm.getMember().getMemberName())
                .id(alarm.getId().toString())
                .body(alarm.getBody())
                .createdAt(alarm.getCreatedAt().toString())
                .build();
    }


    public static AlarmResponseDTO toAlarmResponseDTO(String memberName,String emitterId){
        return AlarmResponseDTO.builder()
                .memberName(memberName)
                .emitterId(emitterId)
                .build();

    }
}
