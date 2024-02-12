package com.example.umcmatchingcenter.dto.MatchingDTO;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.ProjectDTO.ProjectResponseDTO;
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
        private List<MatchingProjectRecruitmentDTO> recruitments;
    }

    // 매칭 프로젝트 상세 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingProjectDTO {
        private Long projectId;
        private Long pmId;
        private String name;
        private String image;
        private String introduction;
        private String body;
        private List<MatchingProjectRecruitmentDTO> recruitments;
        private List<ProjectMemberDTO> members;
        private LocalDateTime createAt;
    }

    // 모집 현황 조회
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchingProjectRecruitmentDTO {
        private MemberPart part;
        private boolean isRecruitmentFinished;
        private int nowRecruitment;
        private int totalRecruitment;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddMatchingProjectResponseDTO{
        private Long ProjectId;
        private String name;
    }
}
