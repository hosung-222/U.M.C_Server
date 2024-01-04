package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.Part;
import com.example.umcmatchingcenter.dto.LoginResponseDto;
import com.example.umcmatchingcenter.dto.MemberRequestDto;
import com.example.umcmatchingcenter.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .userId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static Member toMember(MemberRequestDto.JoinDto request){
        Part part = null;
        switch (request.getPart()){
            case 1:
                part = Part.SPRING;
                break;
            case 2:
                part = Part.NODE;
                break;
            case 3:
                part = Part.iOS;
                break;
            case 4:
                part = Part.ANDROID;
                break;
            case 5:
                part = Part.WEB;
                break;
            case 6:
                part = Part.PM;
                break;
            case 7:
                part = Part.DESIGN;
                break;
        }

        return Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .nickName(request.getNickName())
                .universityId(request.getUniversityId())
                .phoneNumber(request.getPhoneNumber())
                .part(part)
                .generation(request.getGeration())
                .build();
    }
}
