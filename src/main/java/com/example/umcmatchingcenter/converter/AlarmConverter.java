package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.dto.AlarmDTO.AlarmResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AlarmConverter {
    public static Alarm toAlarm(Member receiver, AlarmType alarmType, String content){

        return Alarm.builder()
                .member(receiver)
                .alarmType(alarmType)
                .body(content)
                .title(alarmType.getTitle())
                .build();
    }

    public static AlarmResponseDTO.AlarmViewDTO toAlarmViewDTO(Alarm alarm){
        return AlarmResponseDTO.AlarmViewDTO.builder()
                .title(alarm.getAlarmType().getTitle())
                .body(alarm.getBody())
                .isConfirmed(alarm.getIsConfirmed())
                .createdAt(alarm.getCreatedAt().toString())
                .build();
    }

    public static AlarmResponseDTO.SseAlarmViewDTO toSseAlarmViewDTO(Alarm alarm){
        return AlarmResponseDTO.SseAlarmViewDTO.builder()
                .title(alarm.getAlarmType().getTitle())
                .body(alarm.getBody())
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

    public static AlarmResponseDTO.DeleteAlarmDTO toDeleteAlarmDTO(String memberName, int deleteCount){
        return AlarmResponseDTO.DeleteAlarmDTO.builder()
                .deleteCount(deleteCount)
                .memberName(memberName)
                .build();
    }

    public static List<Alarm> toAlarmList(List<Member> memberList, AlarmType alarmType, String content){
        List<Alarm> alarmList = memberList.stream()
                .filter(member -> !member.getRole().equals(MemberRole.ROLE_ADMIN))
                .map(member -> AlarmConverter.toAlarm(member, alarmType, content))
                .collect(Collectors.toList());

        return alarmList;
    }
}
