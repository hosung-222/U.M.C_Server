package com.example.umcmatchingcenter.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        private Long id;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyInfoDTO {
        private String universityName;
        private String part;
        private String phoneNumber;
        private String portfolio;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor

    public static class ChallengerInfoDTO {
        private String name;
        private int generation;
        private String nameNickname;
        private String part;
        private int matchCount;
        private String matchingStatus;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApplyTeamDTO {
        private int round;
        private String teamName;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartResultDTO{
        private Long memberId;
        private String nameNickname;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequestDTO {
        private Long memberId;
        private int generation;
        private String nameNickname;
        private String part;
    }
}
