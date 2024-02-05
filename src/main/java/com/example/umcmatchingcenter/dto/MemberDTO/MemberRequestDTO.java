package com.example.umcmatchingcenter.dto.MemberDTO;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

public class MemberRequestDTO {

    @Getter
    @Setter
    public static class JoinDTO {
        @NotBlank
        @Email(message = "잘못된 이메일 형식입니다.")
        private String email;
        @NotNull
        private String memberName;
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotNull
        private String password;
        @NotNull
        private String nameNickname;
        @NotNull
        private MemberPart part;
        @NotNull
        private Long universityId;
        @NotNull
        @Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$", message = "핸드폰 번호의 약식과 맞지 않습니다.")
        private String phoneNumber;
        @NotNull
        private Integer generation;

        private String portfolio;
    }


    @Getter
    @Setter
    public static class UpdateMyInfoDTO{

        private String portfolio;

        @NotBlank
        private String phoneNumber;

    }

    @Getter
    @Setter
    public static class UpdateAdminInfoDTO{

        @NotBlank
        private String phoneNumber;

        @NotBlank
        private String branch;

    }
}
