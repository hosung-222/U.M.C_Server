package com.example.umcmatchingcenter.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String memberName;
    private String accessToken;
    private String refreshToken;
}
