package com.example.umcmatchingcenter.service.myProjectService;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryService {

    private final ProjectRepository projectRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;
    private final ProjectVolunteerRepository applicationRepository;

    public Optional<ProjectVolunteer> getProjectVolunteer(Long memberId) {
        Optional<ProjectVolunteer> foundApplication = applicationRepository.findByMemberId(memberId);
        return foundApplication;
    }

    public Project getProject() {

        String memberName = SecurityUtil.getCurrentMember();
        Optional<Member> member = memberRepository.findMemberByEmail(memberName);

        if (member.isPresent()) {
            Optional<Project> project = projectRepository.findByMember(member.get());
            if (project.isPresent()) {
                return project.get();
            }
        }
        return null;
    }

    public Recruitment getRecruitment(MemberPart part, Project project) {
        Optional<Recruitment> foundRecruitment = recruitmentRepository.findByPartAndProject(part,project);
        if (foundRecruitment.isPresent() && foundRecruitment != null) {
            return foundRecruitment.get();
        }
        return null;
    }

    public Member getMember(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isPresent() && foundMember != null) {
            return foundMember.get();
        }
        return null;
    }

}
