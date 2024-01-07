package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.MemberDto.LoginResponseDto;
import com.example.umcmatchingcenter.dto.MemberDto.MemberRequestDto;
import com.example.umcmatchingcenter.dto.MemberDto.MemberResponseDto;

import java.time.LocalDateTime;


public class MemberConverter {

    public static LoginResponseDto toLoginResponseDto(String email, String accessToken,String refreshToken){
        return LoginResponseDto.builder()
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
    public static MemberResponseDto.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDto.JoinResultDTO.builder()
                .id(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static Member toMember(MemberRequestDto.JoinDto request){
        MemberPart memberPart = null;
        switch (request.getPart()){
            case 1:
                memberPart = MemberPart.SPRING;
                break;
            case 2:
                memberPart = MemberPart.NODE;
                break;
            case 3:
                memberPart = MemberPart.iOS;
                break;
            case 4:
                memberPart = MemberPart.ANDROID;
                break;
            case 5:
                memberPart = MemberPart.WEB;
                break;
            case 6:
                memberPart = MemberPart.PM;
                break;
            case 7:
                memberPart = MemberPart.DESIGN;
                break;
        }

        return Member.builder()
                .memberName(request.getMemberName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nameNickname(request.getNameNickname())
                .phoneNumber(request.getPhoneNumber())
                .part(memberPart)
                .generation(request.getGeration())
                .build();
    }
}
