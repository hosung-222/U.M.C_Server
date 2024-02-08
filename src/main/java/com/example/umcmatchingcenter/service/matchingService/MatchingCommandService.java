package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.converter.ProjectConverter;
import com.example.umcmatchingcenter.converter.ProjectVolunteerConverter;
import com.example.umcmatchingcenter.converter.RecruitmentConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingRequestDTO;
import com.example.umcmatchingcenter.repository.*;
import com.example.umcmatchingcenter.service.branchService.BranchQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingCommandService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final ProjectVolunteerRepository projectVolunteerRepository;
    private final BranchQueryService branchQueryService;
    private final MemberQueryService memberQueryService;
    private final S3UploadService s3UploadService;
    private final RecruitmentRepository recruitmentRepository;
    private final MatchingQueryServiceImpl matchingQueryService;

    public void processBranch(Branch branch) {
        List<Recruitment> branchRecruitments = branchQueryService.getBranchRecruitments(branch);
        for (Recruitment recruitment : branchRecruitments) {
            processRecruitment(branch, recruitment);
        }
    }

    public void processRecruitment(Branch branch, Recruitment recruitment) {
        List<Member> branchNonMatchingMember = memberQueryService.getNonMatchingMember(branch, recruitment.getPart());
        selectAndPassMembers(branchNonMatchingMember, recruitment.getNowRecruitment(), recruitment.getProject(), recruitment);
    }


    public void selectAndPassMembers(List<Member> branchNonMatchingMember, int numberOfMembersToSelect, Project project, Recruitment recruitment) {
        Random random = new Random();
        int maxSize = branchNonMatchingMember.size();
        int numberOfMembersSelected = Math.min(numberOfMembersToSelect, maxSize);
        for (int i = 0; i < numberOfMembersSelected; i++) {
            int randomIndex = random.nextInt(maxSize);
            Member selectedMember = branchNonMatchingMember.get(randomIndex);
            selectedMember.setMatchingStatus(MemberMatchingStatus.MATCH);
            memberRepository.save(selectedMember);

            ProjectVolunteer projectVolunteer = ProjectVolunteerConverter.toProjectVolunteer(project, selectedMember, 4);
            projectVolunteerRepository.save(projectVolunteer);
            recruitment.minusRecruitment();
        }
    }

    public Project addMatchingProjects(MatchingRequestDTO.AddMatchingProjectRequestDTO request, String memberName, MultipartFile image){
        Member pm = memberQueryService.findMemberByName(memberName);
        String imageUrl = s3UploadService.uploadFile(image);

        Project project = ProjectConverter.toProject(request, pm, pm.getUniversity().getBranch(), imageUrl);

        List<Recruitment> recruitmentList = getRecruitmentList(request.getPartCounts(), project);
        recruitmentRepository.saveAll(recruitmentList);

        return matchingRepository.save(project);
    }

    private List<Recruitment> getRecruitmentList(Map<MemberPart, Integer> partCount, Project project){
        List<Recruitment> recruitmentList = new ArrayList<>();
        partCount.forEach(
                (memberPart, count) ->{
                    Recruitment recruitment = RecruitmentConverter.toRecruitment(project, memberPart,count);
                    recruitmentList.add(recruitment);
                }
        );
        return recruitmentList;
    }

    public void updateMatchingProjects(Long projectId, MatchingRequestDTO.UpdateMatchingProjectRequestDTO request,MultipartFile image){

        Project project = matchingQueryService.findProject(projectId);

        project.updateProject(request);
        updateRecruitment(request.getPartCounts(), project);

        String projectImage = project.getImage();
        if (image != null) {
            projectImage = s3UploadService.uploadFile(image);
        }

        project.updateImage(projectImage);

        matchingRepository.save(project);
    }

    private void updateRecruitment(Map<MemberPart, Integer> partCount, Project project){
        List<Recruitment> recruitmentList = getRecruitmentList(partCount, project);
        recruitmentRepository.deleteAllByProject(project);
        recruitmentRepository.saveAll(recruitmentList);
    }
}
