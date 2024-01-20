package com.example.umcmatchingcenter.dto.MemberDTO;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class LoginRequestDTO {
    @NotNull
    private String memberName;

    @NotNull
    private String password;
}
