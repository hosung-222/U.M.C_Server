package com.example.umcmatchingcenter.service.queryService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MyProjectHandler;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.jwt.SecurityUtil;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.project.ProjectRepository;
import com.example.umcmatchingcenter.repository.project.ProjectVolunteerRepository;
import com.example.umcmatchingcenter.repository.project.RecruitmentRepository;
import com.example.umcmatchingcenter.service.queryService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryService {

    private final MemberQueryService memberQueryService;
    private final ProjectRepository projectRepository;

    public Project getProject() {

        Member currentLoginMember = memberQueryService.getCurrentLoginMember();

        if (currentLoginMember != null) {
            Optional<Project> project = projectRepository.findByMember(currentLoginMember);
            if (project.isPresent()) {
                return project.get();
            }
        }
        return null;
    }

    public Boolean isFull(Long memberId) {

        if (memberQueryService.getMember(memberId) == null) {
            throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
        }
        MemberPart memberPart = memberQueryService.getMember(memberId).getPart();
        Optional<Recruitment> recruitment = getProject().getRecruitments().stream()
                .filter(part -> part.getPart().equals(memberPart)).findAny();
        if (recruitment.get().getNowRecruitment() == recruitment.get().getTotalRecruitment()){
            return true;
        }
        return false;
    }

}
