package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Project, Long> {

    Page<Project> findAllByBranchAndStatusOrderByCreatedAt(Branch branch, ProjectStatus status, PageRequest pageRequest);

    Optional<Project> findByIdAndStatus(Long projectId, ProjectStatus status);

}
