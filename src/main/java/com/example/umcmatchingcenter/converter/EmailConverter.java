package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.enums.EmailCertificationResult;
import com.example.umcmatchingcenter.dto.MemberDTO.EmailResponseDTO;

public class EmailConverter {

    public static EmailResponseDTO.AuthCodeResultDTO toEmailAuthCodeResultDto(String email, String authCode){
        return EmailResponseDTO.AuthCodeResultDTO.builder()
                .email(email)
                .authCode(authCode)
                .build();
    }

    public static EmailResponseDTO.AuthCodeCertificaitionResultDTO toEmailCertificaitionResultDto(String email){
        return EmailResponseDTO.AuthCodeCertificaitionResultDTO.builder()
                .email(email)
                .emailCertificationResult(EmailCertificationResult.SUCCESS)
                .build();
    }
}
