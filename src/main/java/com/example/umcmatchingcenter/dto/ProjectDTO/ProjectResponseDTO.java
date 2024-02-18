package com.example.umcmatchingcenter.dto.ProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponseDTO {
    // 프로젝트 목록 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectListDTO {
        private List<ProjectPreViewDTO> projectList;
        private Integer listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectPreViewDTO {
        private Long projectId;
        private String name;
        private String image;
        private String introduction;
    }

    // 프로젝트 상세 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDTO {
        private Long projectId;
        private String name;
        private String image;
        private String introduction;
        private String body;
        private List<ProjectMemberDTO> members;
        private String createAt;
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
        private Integer generation;
        private String university;
    }
}
