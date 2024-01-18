package com.example.umcmatchingcenter.service.myProjectService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MyProjectHandler;
import com.example.umcmatchingcenter.converter.myProject.ApplicantsConverter;
import com.example.umcmatchingcenter.converter.myProject.MyProjectConverter;
import com.example.umcmatchingcenter.converter.myProject.ProjectConverter;
import com.example.umcmatchingcenter.converter.myProject.TotalMatchingConverter;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.projectDTO.ApplicantInfoResponseDTO;
import com.example.umcmatchingcenter.dto.projectDTO.MyProjectResponseDTO;
import com.example.umcmatchingcenter.dto.projectDTO.PartMatchingResponseDTO;
import com.example.umcmatchingcenter.dto.projectDTO.TotalMatchingResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectQueryService projectQueryService;

    public MyProjectResponseDTO myProject() {
        return MyProjectConverter.toMyProjectResponseDto(
                getCurrentMatchingNum(),
                getPartRecruitment(),
                getCompetitionRate(),
                getProjectApplicants());
    }

    @Transactional
    public TotalMatchingResponseDTO getCurrentMatchingNum() {
        if (projectQueryService.getProject() != null) {
            List<Recruitment> recruitments = projectQueryService.getProject().getRecruitments();

            int nowMatchingNum = recruitments.stream()
                    .mapToInt(Recruitment::getNowRecruitment)
                    .sum();

            int totalMatchingNum = recruitments.stream()
                    .mapToInt(Recruitment::getTotalRecruitment)
                    .sum();

            return TotalMatchingConverter.toTotalMatchingResponseDTO(nowMatchingNum, totalMatchingNum);
        }
        return TotalMatchingConverter.toTotalMatchingResponseDTO(0, 0);
    }

    @Transactional
    public List<PartMatchingResponseDTO> getPartRecruitment() {

        return Optional.ofNullable(projectQueryService.getProject())
                .map(Project::getRecruitments)
                .orElse(Collections.emptyList())
                .stream()
                .map(recruitment -> ProjectConverter.toPartMatchingResponseDto(
                        recruitment.getPart(),
                        recruitment.getNowRecruitment(),
                        recruitment.getTotalRecruitment()))
                .collect(Collectors.toList());
    }

    @Transactional
    public double getCompetitionRate() {
        if (projectQueryService.getProject() != null) {
            List<ProjectVolunteer> ProjectVolunteers = projectQueryService.getProject().getProjectVolunteerList();
            List<Recruitment> recruitments = projectQueryService.getProject().getRecruitments();

            int totalRecruitmentNum = recruitments.stream()
                    .mapToInt(Recruitment::getTotalRecruitment)
                    .sum();

            return calculateCompetitionRate(totalRecruitmentNum, ProjectVolunteers.size());
        }
        return 0;
    }

    @Transactional
    public List<ApplicantInfoResponseDTO> getProjectApplicants() {
        if (projectQueryService.getProject() != null) {
            List<ProjectVolunteer> projectVolunteer = projectQueryService.getProject().getProjectVolunteerList();

            return projectVolunteer.stream()
                    .map(application -> ApplicantsConverter.toProjectApplicantsResponseDto(
                            application.getMember().getNameNickname(),
                            application.getMember().getUniversity().getName(),
                            application.getMember().getPart(),
                            application.getMember().getProfileImage(),
                            application.getMember().getMatchingStatus()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    //합격 서비스
    @Transactional
    public String pass(Long memberId) {

        // 인원 만석 시 예외처리
        if (isFull(memberId)) {
            throw new MyProjectHandler(ErrorStatus.NO_MORE_APPLICANT);
        }

        if (projectQueryService.getProjectVolunteer(memberId) != null && projectQueryService.getProjectVolunteer(memberId).isPresent()) {

            projectQueryService.getMember(memberId).setProject(projectQueryService.getProject());
            projectQueryService.getMember(memberId).setMatchingStatus(MemberMatchingStatus.MATCH);
            Recruitment recruitment = projectQueryService.getRecruitment(
                    projectQueryService.getMember(memberId).getPart(),
                    projectQueryService.getProject());
            int nowRecruitment = recruitment.getNowRecruitment();
            recruitment.setNowRecruitment(++nowRecruitment);
            if(isFull(memberId)){
                recruitment.setRecruitmentStatus(RecruitmentStatus.FULL);
            }
            return projectQueryService.getMember(memberId).getNameNickname();
        }
        throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
    }

    @Transactional
    public String fail(Long memberId) {

        Optional<ProjectVolunteer> foundApplication = projectQueryService.getProjectVolunteer(memberId);

        if (foundApplication != null && foundApplication.isPresent()) {
            projectQueryService.getMember(memberId).setMatchingStatus(MemberMatchingStatus.NON);
            return projectQueryService.getMember(memberId).getNameNickname();
        }
        throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
    }

    public Boolean isFull(Long memberId) {

        if (projectQueryService.getMember(memberId) == null) {
            throw new MyProjectHandler(ErrorStatus.NO_SUCH_APPLICANT);
        }
        MemberPart memberPart = projectQueryService.getMember(memberId).getPart();
        Optional<Recruitment> recruitment = projectQueryService.getProject().getRecruitments().stream()
                .filter(part -> part.getPart().equals(memberPart)).findAny();
        if (recruitment.get().getNowRecruitment() == recruitment.get().getTotalRecruitment()){
            return true;
        }
        return false;
    }

    private double calculateCompetitionRate(int totalRecruitment, int totalApplicants) {
        return Math.ceil((double) totalApplicants / totalRecruitment * 100) / 100.0;
    }

}
