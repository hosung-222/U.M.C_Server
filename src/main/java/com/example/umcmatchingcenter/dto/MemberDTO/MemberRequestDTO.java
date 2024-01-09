package com.example.umcmatchingcenter.dto.MemberDTO;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberRequestDTO {

    @Getter
    @Setter
    public static class JoinDto{
        @NotBlank
        @Email(message = "잘못된 이메일 형식입니다.")
        String email;
        @NotNull
        String memberName;
     //   @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotNull
        String password;
        @NotNull
        String nameNickname;
        @NotNull
        Integer part;
        @NotNull
        Long universityId;
        @NotNull
       // @Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$", message = "핸드폰 번호의 약식과 맞지 않습니다. xxx-xxxx-xxxx")
        String phoneNumber;
        @NotNull
        Integer geration;

        String portfolio;
    }
}
