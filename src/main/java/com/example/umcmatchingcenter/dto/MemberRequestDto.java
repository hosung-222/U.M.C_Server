package com.example.umcmatchingcenter.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MemberRequestDto {

    @Getter
    @Setter
    public static class JoinDto{
        @NotBlank
        String email;
        @NotNull
        String name;
        @NotNull
        String password;
        @NotNull
        String nickName;
        @NotNull
        Long universityId;
        @NotNull
        Integer part;
        @NotNull
        String phoneNumber;
        @NotNull
        Integer geration;

        String portfolio;
    }
}
