package com.example.umcmatchingcenter.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailRequestDto {

    @Getter
    public static class AuthCodeDto {
        @Email(message = "잘못된 이메일 형식입니다.")
        @NotEmpty
        private String email;
    }

    @Getter
    public static class CertificationDto {

        @NotEmpty
        private String email;
        @NotEmpty
        private String authCode;
    }
}
