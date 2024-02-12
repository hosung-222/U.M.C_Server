package com.example.umcmatchingcenter.converter.matching;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MatchingConverter {
    // 프로젝트 전체 조회
    public static MatchingResponseDTO.MatchingProjectPreViewDTO toMatchingProjectPreViewDTO(Project project){
        // 모집 현황 조회
        List<MatchingResponseDTO.MatchingProjectRecruitmentDTO> recruitmentDTOs = project.getRecruitments().stream()
                .map(recruitment -> MatchingResponseDTO.MatchingProjectRecruitmentDTO.builder()
                        .part(recruitment.getPart())
                        .isRecruitmentFinished(recruitment.getRecruitmentStatus() == RecruitmentStatus.FULL)
//                        .nowRecruitment(recruitment.getNowRecruitment())
//                        .totalRecruitment(recruitment.getTotalRecruitment())
                        .build())
                .collect(Collectors.toList());

        return MatchingResponseDTO.MatchingProjectPreViewDTO.builder()
                .projectId((project.getId()))
                .name(project.getName())
                .image(project.getProfileImageUrl())
                .introduction(project.getIntroduction())
                .recruitments(recruitmentDTOs)
                .build();
    }

    public static MatchingResponseDTO.MatchingProjectListDTO toProjectPreViewListDTO(List<Project> projectList){

        List<MatchingResponseDTO.MatchingProjectPreViewDTO> projectPreViewDTOList = projectList.stream()
                .map(MatchingConverter::toMatchingProjectPreViewDTO).collect(Collectors.toList());

        return MatchingResponseDTO.MatchingProjectListDTO.builder()
                .listSize(projectPreViewDTOList.size())
                .projectList(projectPreViewDTOList)
                .build();
    }

    // 프로젝트 상세 조회
    public static MatchingResponseDTO.MatchingProjectDTO toMatchingProjectDetailDTO(Project project) {
        // 모집 현황 조회
        List<MatchingResponseDTO.MatchingProjectRecruitmentDTO> recruitmentDTOs = project.getRecruitments().stream()
                .map(recruitment -> MatchingResponseDTO.MatchingProjectRecruitmentDTO.builder()
                        .part(recruitment.getPart())
                        .isRecruitmentFinished(recruitment.getRecruitmentStatus() == RecruitmentStatus.FULL)
                        .nowRecruitment(recruitment.getNowRecruitment())
                        .totalRecruitment(recruitment.getTotalRecruitment())
                        .build())
                .collect(Collectors.toList());

        // 참여 멤버 조회
        List<MatchingResponseDTO.ProjectMemberDTO> memberDTOs = project.getMembers().stream()
                .map(member -> MatchingResponseDTO.ProjectMemberDTO.builder()
                        .id(member.getId())
                        .nameNickname(member.getNameNickname())
                        .profileImage(member.getProfileImage())
                        .part(String.valueOf(member.getPart()))
                        .build())
                .collect(Collectors.toList());

        return MatchingResponseDTO.MatchingProjectDTO.builder()
                .projectId(project.getId())
                .pmId(project.getPm().getId())
                .name(project.getName())
                .image(project.getProfileImageUrl())
                .introduction(project.getIntroduction())
                .body(project.getBody())
                .recruitments(recruitmentDTOs)
                .members(memberDTOs)
                .createAt(project.getCreatedAt())
                .build();
    }

    public static MatchingResponseDTO.AddMatchingProjectResponseDTO toAddMatchingProjectResponseDTO(Project project){
        return MatchingResponseDTO.AddMatchingProjectResponseDTO.builder()
                .ProjectId(project.getId())
                .name(project.getName())
                .build();
    }
}
