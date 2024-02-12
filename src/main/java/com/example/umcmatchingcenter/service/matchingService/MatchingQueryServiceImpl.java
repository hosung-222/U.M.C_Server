package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MatchingHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
import com.example.umcmatchingcenter.converter.matching.MatchingConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingResponseDTO;
import com.example.umcmatchingcenter.repository.ImageRepository;
import com.example.umcmatchingcenter.repository.MatchingRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingQueryServiceImpl implements MatchingQueryService {

    private static final int PAGING_SIZE = 15;

    private final MatchingRepository matchingRepository;
    private final MemberQueryService memberQueryService;
    private final ImageRepository imageRepository;

    @Override
    public Project findProject(Long id) {
        Optional<Project> project = matchingRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectHandler(ErrorStatus.PROJECT_NOT_EXIST);
        }
        return project.get();
    }


    @Override
    public List<Project> getProjectList(Branch branch, ProjectStatus status, Integer page) {
        try {
            return matchingRepository.findAllByBranchAndStatusOrderByCreatedAt(branch, status, PageRequest.of(page, PAGING_SIZE)).getContent();
        } catch (Exception e) {
            throw new RuntimeException("프로젝트 목록을 가져오는 중 오류가 발생했습니다", e);
        }
    }

    @Override
    public MatchingResponseDTO.MatchingProjectDTO getProjectDetail(Long projectId, String memberName) {

        Project project = matchingRepository.findByIdAndStatus(projectId, ProjectStatus.PROCEEDING)
                .orElseThrow(()->new ProjectHandler(ErrorStatus.PROJECT_NOT_PROCEEDING));
        Member member = memberQueryService.findMemberByName(memberName);

        Map<Long, String> Images = project.getImages().stream()
                .collect(Collectors.toMap(Image::getId, Image::getS3ImageUrl));

        return MatchingConverter.toMatchingProjectDetailDTO(project, member.getId(), Images);

    }

//    @Override
//    public Project getProjectDetail(Long projectId) {
//        return projectRepository.getProjectDetail(projectId);
//    }

    @Override
    public boolean existProject(Long projectId) {
        return matchingRepository.existsById(projectId);
    }

}
