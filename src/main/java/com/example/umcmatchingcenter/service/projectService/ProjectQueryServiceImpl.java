package com.example.umcmatchingcenter.service.projectService;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryServiceImpl implements ProjectQueryService{

    private static final int PAGING_SIZE = 15;

    private final ProjectRepository projectRepository;

    @Override
    public Optional<Project> findProject(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> getProjectList(ProjectStatus status, Integer page) {
        try {
            return projectRepository.findAllByStatusOrderByCreatedAt(status, PageRequest.of(page, PAGING_SIZE)).getContent();
        } catch (Exception e) {
            throw new RuntimeException("프로젝트 목록을 가져오는 중 오류가 발생했습니다", e);
        }
    }

}
