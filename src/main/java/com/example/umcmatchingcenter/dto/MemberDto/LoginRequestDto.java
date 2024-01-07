package com.example.umcmatchingcenter.dto.MemberDto;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotNull
    String memberName;

    @NotNull
    String password;
}
