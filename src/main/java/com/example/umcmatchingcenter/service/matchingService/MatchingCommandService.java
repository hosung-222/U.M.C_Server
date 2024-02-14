package com.example.umcmatchingcenter.service.matchingService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MatchingHandler;
import com.example.umcmatchingcenter.converter.ImageConverter;
import com.example.umcmatchingcenter.converter.ProjectConverter;
import com.example.umcmatchingcenter.converter.ProjectVolunteerConverter;
import com.example.umcmatchingcenter.converter.RecruitmentConverter;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.ProjectImage;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingRequestDTO;
import com.example.umcmatchingcenter.repository.*;
import com.example.umcmatchingcenter.service.branchService.BranchQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;

import java.util.*;
import java.util.stream.Collectors;

import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class MatchingCommandService {
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final ProjectVolunteerRepository projectVolunteerRepository;
    private final BranchQueryService branchQueryService;
    private final MemberQueryService memberQueryService;
    private final RecruitmentRepository recruitmentRepository;
    private final MatchingQueryServiceImpl matchingQueryService;
    private final ImageRepository imageRepository;
    private final ProjectImageRepository projectImageRepository;
    private final S3UploadService s3UploadService;

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

    public Project addMatchingProjects(MatchingRequestDTO.AddMatchingProjectRequestDTO request, String memberName){
        Member pm = memberQueryService.findMemberByName(memberName);

        Project project = ProjectConverter.toProject(request, pm, pm.getUniversity().getBranch());
        matchingRepository.save(project);
        setProfileImage(request.getProfileImageId(), project);
        mappingProjectAndImage(request.getImageIdList(), project);

        List<Recruitment> recruitmentList = getRecruitmentList(request.getPartCounts(), project);
        recruitmentRepository.saveAll(recruitmentList);

        return project;
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

    private void mappingProjectAndImage(List<Long> imageList, Project project){
        List<Image> images = imageRepository.findAllById(imageList);
        List<ProjectImage> projectImages = images.stream()
                .map(image -> ImageConverter.toProjectImage(project, image))
                .collect(Collectors.toList());
        projectImageRepository.saveAll(projectImages);

    }

    private void setProfileImage(Long profilImageId, Project project){
        Image image = imageRepository.findById(profilImageId)
                .orElseThrow(() -> new MatchingHandler(ErrorStatus.IMAGE_NOT_EXIST));
        ProjectImage profileImage = ImageConverter.toProjectImage(project, image);
        profileImage.setProfile();
        projectImageRepository.save(profileImage);

    }

    public void updateMatchingProjects(Long projectId, MatchingRequestDTO.UpdateMatchingProjectRequestDTO request){

        Project project = matchingQueryService.findProject(projectId);
        if(project.getProfileImage().getId() != request.getProfileImageId()){
            setProfileImage(request.getProfileImageId(),project);
        }
        deleteImages(request.getDeleteImageIdList());
        mappingProjectAndImage(request.getImageIdList(), project);

        project.updateProject(request);
        updateRecruitment(request.getPartCounts(), project);

        matchingRepository.save(project);
    }

    private void updateRecruitment(Map<MemberPart, Integer> partCount, Project project){
        List<Recruitment> recruitmentList = getRecruitmentList(partCount, project);
        recruitmentRepository.deleteAllByProject(project);
        recruitmentRepository.saveAll(recruitmentList);
    }

    private void deleteImages(List<Long> deleteImageIdList){
        List<Image> deleteS3ImageList = imageRepository.findAllById(deleteImageIdList);

        List<ProjectImage> deleteProjectImageList = deleteS3ImageList.stream()
                .peek(image -> s3UploadService.delete(image.getS3ImageUrl()))
                .map(image -> projectImageRepository.findByImage(image))
                .collect(Collectors.toList());

        projectImageRepository.deleteAll(deleteProjectImageList);
    }

}
