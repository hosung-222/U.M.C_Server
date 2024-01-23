package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;

import com.example.umcmatchingcenter.dto.ProjectDTO.ProjectResponseDTO;


import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {
    // 프로젝트 전체 조회
    public static ProjectResponseDTO.ProjectPreViewDTO toProjectPreViewDTO(Project project){
        return ProjectResponseDTO.ProjectPreViewDTO.builder()
                .projectId((project.getId()))
                .name(project.getName())
                .image(project.getImage())
                .introduction(project.getIntroduction())
                .build();
    }

    public static ProjectResponseDTO.ProjectListDTO toProjectPreViewListDTO(List<Project> projectList){

        List<ProjectResponseDTO.ProjectPreViewDTO> projectPreViewDTOList = projectList.stream()
                .map(ProjectConverter::toProjectPreViewDTO).collect(Collectors.toList());

        return ProjectResponseDTO.ProjectListDTO.builder()
                .listSize(projectPreViewDTOList.size())
                .projectList(projectPreViewDTOList)
                .build();
    }

    // 프로젝트 상세 조회
    public static ProjectResponseDTO.ProjectDTO toProjectDetailDTO(Project project) {
        // 참여 멤버 조회
        List<ProjectResponseDTO.ProjectMemberDTO> memberDTOs = project.getMembers().stream()
                .map(member -> ProjectResponseDTO.ProjectMemberDTO.builder()
                        .id(member.getId())
                        .nameNickname(member.getNameNickname())
                        .profileImage(member.getProfileImage())
                        .part(String.valueOf(member.getPart()))
                        .build())
                .collect(Collectors.toList());

        return ProjectResponseDTO.ProjectDTO.builder()
                .projectId(project.getId())
                .name(project.getName())
                .image(project.getImage())
                .introduction(project.getIntroduction())
                .body(project.getBody())
                .members(memberDTOs)
                .createAt(project.getCreatedAt())
                .build();
    }
}
