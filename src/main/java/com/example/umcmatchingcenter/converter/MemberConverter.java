package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.MemberDTO.LoginResponseDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberRequestDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;

import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.AcceptResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ChallengerInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.DepartResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.MyInfoDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.RejectResultDTO;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.SignUpRequestMemberDTO;
import java.time.LocalDateTime;


public class MemberConverter {


    public static LoginResponseDTO toLoginResponseDto(String memberName, String accessToken, String refreshToken, String memberRole){
        return LoginResponseDTO.builder()
                .memberRole(memberRole)
                .memberName(memberName)
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

        if(request.getPart().equals("SPRINGBOOT")){
            memberPart = MemberPart.SPRINGBOOT;
        }else if(request.getPart().equals("NODEJS")){
            memberPart = MemberPart.NODEJS;
        }else if(request.getPart().equals("IOS")){
            memberPart = MemberPart.IOS;
        }else if(request.getPart().equals("ANDROID")){
            memberPart = MemberPart.ANDROID;
        }else if(request.getPart().equals("WEB")){
            memberPart = MemberPart.WEB;
        }else if(request.getPart().equals("PLAN")){
            memberPart = MemberPart.PLAN;
        }else if(request.getPart().equals("DESIGN")){
            memberPart = MemberPart.DESIGN;
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
                .name(member.getMemberName())
                .generation(member.getGeneration())
                .nameNickname(member.getNameNickname())
                .part(member.getPart().toString())
                .matchCount(member.getProjectVolunteerList().size())
                .matchingStatus(member.getMatchingStatus().toString())
                .build();
    }


    public static DepartResultDTO toDepartResultDTO(Member member){
        return DepartResultDTO.builder()
                .memberId(member.getId())
                .nameNickname(member.getNameNickname())
                .build();
    }

    public static SignUpRequestMemberDTO toSignUpRequestDTO(Member member){
        return SignUpRequestMemberDTO.builder()
                .memberId(member.getId())
                .generation(member.getGeneration())
                .nameNickname(member.getNameNickname())
                .part(member.getPart().name())
                .build();
    }

    public static AcceptResultDTO toAcceptResultDTO(Member member){
        return AcceptResultDTO.builder()
                .memberId(member.getId())
                .memberStatus(member.getMemberStatus().name())
                .build();
    }

    public static RejectResultDTO toRejectResultDTO(Member member) {
        return RejectResultDTO.builder()
                .memberId(member.getId())
                .memberStatus(member.getMemberStatus().name())
                .build();
    }

    public static LoginResponseDTO.RenewalAccessTokenResponseDTO toRenewalAccessTokenResponseDTO(String memberName, String memberRole, String newAccessToken){
        return LoginResponseDTO.RenewalAccessTokenResponseDTO.builder()
                .memberName(memberName)
                .memberRole(memberRole)
                .accessToken(newAccessToken)
                .build();
    }
}
