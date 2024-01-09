package com.example.umcmatchingcenter.dto.MemberDTO;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @NotNull
    String memberName;

    @NotNull
    String password;
}
