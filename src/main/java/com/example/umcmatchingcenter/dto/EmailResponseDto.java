package com.example.umcmatchingcenter.dto;

import com.example.umcmatchingcenter.domain.enums.EmailCertificationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResponseDto {

    @Getter
    @Builder
    public static class AuthCodeResultDto {
        private String email;
        private String authCode;

    }

    @Getter
    @Builder
    public static class AuthCodeCertificaitionResultDto {
        private String email;
        private EmailCertificationResult emailCertificationResult;

    }
}
