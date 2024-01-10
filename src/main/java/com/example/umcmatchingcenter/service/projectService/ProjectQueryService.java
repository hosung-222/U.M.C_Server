package com.example.umcmatchingcenter.service.projectService;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;

import java.util.List;
import java.util.Optional;

public interface ProjectQueryService {
    Optional<Project> findProject(Long id);

    List<Project> getProjectList(ProjectStatus status, Integer page);


}
