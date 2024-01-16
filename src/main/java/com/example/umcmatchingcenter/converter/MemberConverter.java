package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;

import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.MyInfoDTO;
import java.time.LocalDateTime;


public class MemberConverter {


    public static LoginResponseDTO toLoginResponseDto(String email, String accessToken, String refreshToken){
        return LoginResponseDTO.builder()
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }
    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .id(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static Member toMember(MemberRequestDTO.JoinDTO request, University university){
        MemberPart memberPart = null;
        switch (request.getPart()){
            case 1:
                memberPart = MemberPart.SPRINGBOOT;
                break;
            case 2:
                memberPart = MemberPart.NODEJS;
                break;
            case 3:
                memberPart = MemberPart.IOS;
                break;
            case 4:
                memberPart = MemberPart.ANDROID;
                break;
            case 5:
                memberPart = MemberPart.WEB;
                break;
            case 6:
                memberPart = MemberPart.PLAN;
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
                .university(university)
                .phoneNumber(request.getPhoneNumber())
                .part(memberPart)
                .generation(request.getGeneration())
                .portfolio(request.getPortfolio())
                .build();
    }

    public static MyInfoDTO toMyInfoDTO(Member member) {
        return MyInfoDTO.builder()
                .universityName(member.getUniversity().getName())
                .part(member.getPart().name())
                .phoneNumber(member.getPhoneNumber())
                .portfolio(member.getPortfolio())
                .build();
    }

    public static ChallengerInfoDTO toChallengerInfoDTO(Member member){
        return ChallengerInfoDTO.builder()
                .nameNickname(member.getMemberName())
                .generation(member.getGeneration())
                .nameNickname(member.getNameNickname())
                .part(member.getPart().toString())
                .matchCount(3)
                .matchingStatus(member.getMatchingStatus().toString())
                .build();
    }
}
