package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.dto.projectDto.ProjectResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectConverter {
    public static ProjectResponseDTO.ProjectPreViewDTO projectPreViewDTO(Project project){
        return ProjectResponseDTO.ProjectPreViewDTO.builder()
                .projectId((project.getId()))
                .name(project.getName())
                .image(project.getImage())
                .introduction(project.getIntroduction())
                .build();
    }

    public static ProjectResponseDTO.ProjectListDTO projectPreViewListDTO(List<Project> projectList){

        List<ProjectResponseDTO.ProjectPreViewDTO> projectPreViewDTOList = projectList.stream()
                .map(ProjectConverter::projectPreViewDTO).collect(Collectors.toList());

        return ProjectResponseDTO.ProjectListDTO.builder()
                .listSize(projectPreViewDTOList.size())
                .projectList(projectPreViewDTOList)
                .build();
    }
}
