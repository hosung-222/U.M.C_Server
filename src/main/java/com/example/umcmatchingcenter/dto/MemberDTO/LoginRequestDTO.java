package com.example.umcmatchingcenter.dto.MemberDTO;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class LoginRequestDTO {
    @NotNull
    @Schema(description = "닉네임", example = "memberName")
    private String memberName;

    @NotNull
    @Schema(description = "비밀번호 ", example = "password (대소문자,숫자,특수문자 포함/ 8~16자")
    private String password;
}
