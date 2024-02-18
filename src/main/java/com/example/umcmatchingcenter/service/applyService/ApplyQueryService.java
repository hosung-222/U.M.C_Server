package com.example.umcmatchingcenter.service.applyService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ApplyHandler;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.ProjectVolunteerRepository;
import com.example.umcmatchingcenter.service.matchingService.MatchingQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.recruitmentService.RecruitmentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyQueryService {

    private final MemberQueryService memberQueryService;
    private final MatchingQueryService matchingQueryService;
    private final RecruitmentQueryService recruitmentQueryService;
    private final ProjectVolunteerRepository projectVolunteerRepository;


    public void isFull(Long projectId) {
        Member member = memberQueryService.getCurrentLoginMember();
        Project project = matchingQueryService.findProject(projectId);
        Recruitment recruitment = recruitmentQueryService.getRecruitment(member.getPart(), project);
        if (recruitment.getRecruitmentStatus().equals(RecruitmentStatus.FULL)) {
            throw new ApplyHandler(ErrorStatus.PART_IS_FULL);
        }
    }

    public void alreadyApply() {
        Member member = memberQueryService.getCurrentLoginMember();
        Optional<ProjectVolunteer> projectVolunteer = projectVolunteerRepository.findByMemberId(member.getId());

        if (projectVolunteer.isPresent()) {
            throw new ApplyHandler(ErrorStatus.ALREADY_APPLY);
        }
    }

    public void isSameGeneration(Long projectId) {
        int memberGeneration = memberQueryService.getCurrentLoginMember().getGeneration();
        Project project = matchingQueryService.findProject(projectId);
        int projectGeneration = project.getBranch().getGeneration();

        if (memberGeneration != projectGeneration) {
            throw new ApplyHandler(ErrorStatus.PROJECT_NOT_PROCEEDING);
        }
    }
}
