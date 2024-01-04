package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.enums.EmailCertificationResult;
import com.example.umcmatchingcenter.dto.EmailResponseDto;

public class EmailConverter {

    public static EmailResponseDto.AuthCodeResultDto toEmailAuthCodeResultDto(String email, String authCode){
        return EmailResponseDto.AuthCodeResultDto.builder()
                .email(email)
                .authCode(authCode)
                .build();
    }

    public static EmailResponseDto.AuthCodeCertificaitionResultDto toEmailCertificaitionResultDto(String email){
        return EmailResponseDto.AuthCodeCertificaitionResultDto.builder()
                .email(email)
                .emailCertificationResult(EmailCertificationResult.SUCCESS)
                .build();
    }
}
