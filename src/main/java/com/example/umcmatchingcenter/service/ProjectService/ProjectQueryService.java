package com.example.umcmatchingcenter.service.ProjectService;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;

import java.util.List;

public interface ProjectQueryService {
    Project getProjectDetail(Long id);

    List<Project> getProjectList(ProjectStatus status, Integer page);

    boolean existProject(Long projectId);

    Project findCompleteProject(Long projectId, ProjectStatus status);
}
