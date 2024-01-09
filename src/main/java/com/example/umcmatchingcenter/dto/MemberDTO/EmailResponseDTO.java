package com.example.umcmatchingcenter.dto.MemberDTO;

import com.example.umcmatchingcenter.domain.enums.EmailCertificationResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailResponseDTO {

    @Getter
    @Builder
    public static class AuthCodeResultDTO {
        private String email;
        private String authCode;

    }

    @Getter
    @Builder
    public static class AuthCodeCertificaitionResultDTO {
        private String email;
        private EmailCertificationResult emailCertificationResult;

    }
}
