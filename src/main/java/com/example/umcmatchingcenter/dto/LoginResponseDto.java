package com.example.umcmatchingcenter.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class LoginResponseDto {
    String email;
    String accessToken;
    String refreshToken;
}
