package com.example.umcmatchingcenter.service.myProjectService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MyProjectHandler;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.ProjectRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MyProjectQueryService {

    private final MemberQueryService memberQueryService;
    private final ProjectRepository projectRepository;

    //현재 로그인된 PM 아이디에 해당하는 프로젝트 찾기
    public Project getProject() {

        Member currentLoginMember = memberQueryService.getCurrentLoginMember();

        if (currentLoginMember != null) {
            Optional<Project> project = projectRepository.findByPm(currentLoginMember);
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

    public void isComplete() {

        long completeRecruitments = getProject().getRecruitments()
                .stream()
                .filter(recruitment -> recruitment.getRecruitmentStatus().equals(RecruitmentStatus.FULL))
                .count();

        int recruitments = getProject().getRecruitments().size();

        if (completeRecruitments == recruitments) {
            getProject().setStatus(ProjectStatus.COMPLETE);
        }
    }

}
