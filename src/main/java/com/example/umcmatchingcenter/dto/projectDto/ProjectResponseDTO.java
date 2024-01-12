package com.example.umcmatchingcenter.dto.projectDto;

import com.example.umcmatchingcenter.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponseDTO {
    // 프로젝트 목록 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectListDTO {
        List<ProjectPreViewDTO> projectList;
        Integer listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectPreViewDTO {
        Long projectId;
        String name;
        String image;
        String introduction;
    }

    // 프로젝트 상세 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDTO {
        Long projectId;
        String name;
        String image;
        String introduction;
        String body;
        List<ProjectMemberDTO> members;
        LocalDateTime createAt;
    }

    // 프로젝트 참여 멤버
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectMemberDTO {
        Long id;
        String nameNickname;
        String profileImage;
        String part;
    }
}
