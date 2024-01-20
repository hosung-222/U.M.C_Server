package com.example.umcmatchingcenter.service.projectService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
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

    // 프로젝트 전체 조회
    @Override
    public List<Project> getProjectList(ProjectStatus status, Integer page) {
        try {
            return projectRepository.findAllByStatusOrderByCreatedAt(status, PageRequest.of(page, PAGING_SIZE)).getContent();
        } catch (Exception e) {
            throw new RuntimeException("프로젝트 목록을 가져오는 중 오류가 발생했습니다", e);
        }
    }

    // 완료된 프로젝트 상세 조회
    @Override
    public Project getProjectDetail(Long projectId) {
        try {
            Optional<Project> target = projectRepository.findByIdAndStatus(projectId, ProjectStatus.COMPLETE);
            return target.get();
        } catch (Exception e) {
            throw new ProjectHandler(ErrorStatus.PROJECT_NOT_COMPLETE);
        }
    }

    @Override
    public boolean existProject(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    @Override
    public Project findCompleteProject(Long projectId, ProjectStatus status) {
        return null;
    }

}
