package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.dto.matchingDto.MatchingResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MatchingConverter {
    // 프로젝트 전체 조회
    public static MatchingResponseDTO.MatchingProjectPreViewDTO toMatchingProjectPreViewDTO(Project project){
        return MatchingResponseDTO.MatchingProjectPreViewDTO.builder()
                .projectId((project.getId()))
                .name(project.getName())
                .image(project.getImage())
                .introduction(project.getIntroduction())
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
                .name(project.getName())
                .image(project.getImage())
                .introduction(project.getIntroduction())
                .body(project.getBody())
                .members(memberDTOs)
                .createAt(project.getCreatedAt())
                .build();
    }
}
