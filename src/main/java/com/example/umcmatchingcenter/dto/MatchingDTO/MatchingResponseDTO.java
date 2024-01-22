package com.example.umcmatchingcenter.dto.MatchingDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MatchingResponseDTO {
    // 매칭 프로젝트 목록 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingProjectListDTO {
        private List<MatchingProjectPreViewDTO> projectList;
        private Integer listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingProjectPreViewDTO {
        private Long projectId;
        private String name;
        private String image;
        private String introduction;
    }

    // 매칭 프로젝트 상세 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingProjectDTO {
        private Long projectId;
        private String name;
        private String image;
        private String introduction;
        private String body;
        private List<ProjectMemberDTO> members;
        private LocalDateTime createAt;
    }

    // 프로젝트 참여 멤버
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectMemberDTO {
        private Long id;
        private String nameNickname;
        private String profileImage;
        private String part;
    }
}
