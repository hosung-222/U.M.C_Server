package com.example.umcmatchingcenter.dto.MemberDTO;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MemberRequestDTO {

    @Getter
    @Setter
    public static class JoinDto{
        @NotBlank
        String email;
        @NotNull
        String memberName;
        @NotNull
        String password;
        @NotNull
        String nameNickname;
        @NotNull
        Integer part;
        @NotNull
        String phoneNumber;
        @NotNull
        Integer geration;

        String portfolio;
    }
}
