package com.example.umcmatchingcenter.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {
    @NotBlank
    @Email(message = "잘못된 이메일 형식입니다.")
    String email;

    @NotNull
    String password;
}
