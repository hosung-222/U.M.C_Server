package com.example.umcmatchingcenter.dto.MemberDTO;

import com.example.umcmatchingcenter.domain.enums.EmailCertificationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class EmailResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthCodeResultDTO {
        private String email;
        private String authCode;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthCodeCertificaitionResultDTO {
        private String email;
        private EmailCertificationResult emailCertificationResult;

    }
}
