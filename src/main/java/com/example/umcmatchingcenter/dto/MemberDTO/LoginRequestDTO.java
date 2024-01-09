package com.example.umcmatchingcenter.dto.MemberDTO;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class LoginRequestDTO {
    @NotNull
    String memberName;

    @NotNull
    String password;
}
