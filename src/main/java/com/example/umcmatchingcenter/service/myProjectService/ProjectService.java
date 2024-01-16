package com.example.umcmatchingcenter.service.myProjectService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MyProjectHandler;
import com.example.umcmatchingcenter.converter.myProject.ApplicantsConverter;
import com.example.umcmatchingcenter.converter.myProject.MyProjectConverter;
import com.example.umcmatchingcenter.converter.myProject.ProjectConverter;
import com.example.umcmatchingcenter.converter.myProject.TotalMatchingConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.Application;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.projectDto.ApplicantInfoResponseDto;
import com.example.umcmatchingcenter.dto.projectDto.MyProjectResponseDto;
import com.example.umcmatchingcenter.dto.projectDto.PartMatchingResponseDto;
import com.example.umcmatchingcenter.dto.projectDto.TotalMatchingResponseDto;
import com.example.umcmatchingcenter.jwt.SecurityUtil;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.project.ApplicationRepository;
import com.example.umcmatchingcenter.repository.project.ProjectRepository;
import com.example.umcmatchingcenter.repository.project.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;
    private final ApplicationRepository applicationRepository;

    public MyProjectResponseDto myProject() {
        return MyProjectConverter.toMyProjectResponseDto(
                getCurrentMatchingNum(),
                getPartRecruitment(),
                getCompetitionRate(),
                getProjectApplicants());
    }

    @Transactional
    public TotalMatchingResponseDto getCurrentMatchingNum() {

        int nowMatchingNum = 0;
        int totalMatchingNum = 0;

        if (getProject() != null) {
            List<Recruitment> recruitments = getProject().getRecruitments();
            for (Recruitment recruitment : recruitments) {
                nowMatchingNum += recruitment.getNowRecruitment();
                totalMatchingNum += recruitment.getTotalRecruitment();
            }
        }
        return TotalMatchingConverter.toTotalMatchingResponseDto(nowMatchingNum,totalMatchingNum);
    }

    @Transactional
    public List<PartMatchingResponseDto> getPartRecruitment() {

        List<PartMatchingResponseDto> result = new ArrayList<>();
        if (getProject() != null) {
            List<Recruitment> recruitments = getProject().getRecruitments();
            for (Recruitment recruitment : recruitments) {
                MemberPart part = recruitment.getPart();
                result.add(ProjectConverter.toPartMatchingResponseDto(
                        part,
                        recruitment.getNowRecruitment(),
                        recruitment.getTotalRecruitment()));
            }
        }
        return result;
    }

    @Transactional
    public double getCompetitionRate() {

        int totalRecruitmentNum = 0;

        if (getProject() != null) {
            List<Application> applications = getProject().getApplications();
            List<Recruitment> recruitments = getProject().getRecruitments();
            for (Recruitment recruitment : recruitments) {
                totalRecruitmentNum += recruitment.getTotalRecruitment();
            }
            return calculateCompetitionRate(totalRecruitmentNum, applications.size());
        }
        return 0;
    }

    @Transactional
    public List<ApplicantInfoResponseDto> getProjectApplicants() {
        ArrayList<ApplicantInfoResponseDto> result = new ArrayList<>();
        if (getProject() != null) {
            List<Application> applications = getProject().getApplications();
            for (Application application : applications) {
                result.add(
                        ApplicantsConverter.toProjectApplicantsResponseDto(
                                application.getMember().getNameNickname(),
                                application.getMember().getUniversity().getName(),
                                application.getMember().getPart(),
                                application.getMember().getProfileImage(),
                                application.getMember().getMatchingStatus())
                );
            }
            return result;
        }
        return null;
    }

    //합격 서비스
    @Transactional
    public String pass(Long memberId) {

        // 인원 만석 시 예외처리
        if (isFull(memberId)) {
            throw new MyProjectHandler(ErrorStatus.NO_MORE_APPLICANT);
        }

        if (getApplication(memberId) != null && getApplication(memberId).isPresent()) {

            getMember(memberId).setProject(getProject());
            getMember(memberId).setMatchingStatus(MemberMatchingStatus.MATCH);
            Recruitment recruitment = getRecruitment(getMember(memberId).getPart(), getProject());
            int nowRecruitment = recruitment.getNowRecruitment();
            recruitment.setNowRecruitment(++nowRecruitment);
            return getMember(memberId).getNameNickname();
        }
        throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
    }

    @Transactional
    public String fail(Long memberId) {

        Optional<Application> foundApplication = getApplication(memberId);

        if (foundApplication != null && foundApplication.isPresent()) {
            getMember(memberId).setMatchingStatus(MemberMatchingStatus.NON);
            return getMember(memberId).getNameNickname();
        }
        throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
    }

    public Boolean isFull(Long memberId) {

        if (getMember(memberId) == null) {
            throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
        }
        MemberPart memberPart = getMember(memberId).getPart();
        Optional<Recruitment> recruitment = getProject().getRecruitments().stream()
                .filter(part -> part.getPart().equals(memberPart)).findAny();
        if (recruitment.get().getNowRecruitment() == recruitment.get().getTotalRecruitment()){
            return true;
        }
        return false;
    }

    private Optional<Application> getApplication(Long memberId) {
        Optional<Application> foundApplication = applicationRepository.findByMember_Id(memberId);
        return foundApplication;
    }

    private Project getProject() {

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

    private Recruitment getRecruitment(MemberPart part,Project project) {
        Optional<Recruitment> foundRecruitment = recruitmentRepository.findByPartAndProject(part,project);
        if (foundRecruitment.isPresent() && foundRecruitment != null) {
            return foundRecruitment.get();
        }
        return null;
    }

    private Member getMember(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isPresent() && foundMember != null) {
            return foundMember.get();
        }
        return null;
    }

    private double calculateCompetitionRate(int totalRecruitment, int totalApplicants) {
        return Math.ceil((double) totalApplicants / totalRecruitment * 100) / 100.0;
    }
}
