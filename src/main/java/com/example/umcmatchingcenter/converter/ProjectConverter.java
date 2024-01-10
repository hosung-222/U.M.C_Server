package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.dto.ProjectDto.ProjectResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {
    public static ProjectResponseDto.ProjectPreViewDTO projectPreViewDTO(Project project){
        return ProjectResponseDto.ProjectPreViewDTO.builder()
                .projectId((project.getId()))
                .name(project.getName())
                .image(project.getImage())
                .introduction(project.getIntroduction())
                .build();
    }

    public static ProjectResponseDto.ProjectListDTO projectPreViewListDTO(List<Project> projectList){

        List<ProjectResponseDto.ProjectPreViewDTO> projectPreViewDTOList = projectList.stream()
                .map(ProjectConverter::projectPreViewDTO).collect(Collectors.toList());

        return ProjectResponseDto.ProjectListDTO.builder()
                .listSize(projectPreViewDTOList.size())
                .projectList(projectPreViewDTOList)
                .build();
    }
}
