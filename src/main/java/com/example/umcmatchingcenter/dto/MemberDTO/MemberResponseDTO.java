package com.example.umcmatchingcenter.dto.MemberDTO;

import com.example.umcmatchingcenter.domain.Branch;
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
        private String profileImage;
        private String email;
        private String name;
        private String universityName;
        private int generation;
        private String part;
        private String phoneNumber;
        private String portfolio;
        private String branch;

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
    public static class SignUpRequestMemberDTO {
        private Long memberId;
        private int generation;
        private String nameNickname;
        private String part;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AcceptResultDTO{
        private Long memberId;
        private String memberStatus;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RejectResultDTO{
        private Long memberId;
        private String memberStatus;
    }
}
