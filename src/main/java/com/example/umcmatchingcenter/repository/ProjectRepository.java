package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findAllByStatusOrderByCreatedAt(ProjectStatus status, PageRequest pageRequest);

//    Project getProjectDetail(Long projectId);

}
