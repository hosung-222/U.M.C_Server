package com.example.umcmatchingcenter.dto.MemberDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDTO {
    String email;
    String accessToken;
    String refreshToken;
}
