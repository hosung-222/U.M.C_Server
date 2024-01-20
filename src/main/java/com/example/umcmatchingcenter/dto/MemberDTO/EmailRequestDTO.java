package com.example.umcmatchingcenter.dto.MemberDTO;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailRequestDTO {

    @Getter
    public static class AuthCodeDTO {
        @Email(message = "잘못된 이메일 형식입니다.")
        @NotEmpty
        private String email;
    }

    @Getter
    public static class CertificationDTO {

        @NotEmpty
        private String email;
        @NotEmpty
        private String authCode;
    }
}
