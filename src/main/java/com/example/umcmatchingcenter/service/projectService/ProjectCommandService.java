package com.example.umcmatchingcenter.service.projectService;

import com.example.umcmatchingcenter.converter.ProjectConverter;
import com.example.umcmatchingcenter.converter.RecruitmentConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.ProjectDTO.ProjectRequestDTO;
import com.example.umcmatchingcenter.repository.ProjectRepository;
import com.example.umcmatchingcenter.repository.RecruitmentRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.s3Service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectCommandService {

    private final MemberQueryService memberQueryService;
    private final S3UploadService s3UploadService;
    private final RecruitmentRepository recruitmentRepository;
    private final ProjectRepository projectRepository;

    public Project addProject(ProjectRequestDTO.AddProjectRequestDto request, String memberName, MultipartFile image){
        Project project = ProjectConverter.toProject(request);

        Member pm = memberQueryService.findMemberByName(memberName);
        project.setPm(pm);
        project.setBranch(pm.getUniversity().getBranch());

        String imageUrl = s3UploadService.uploadFile(image);
        project.setImage(imageUrl);

        List<Recruitment> recruitmentList = getRecruitmentList(request.getPartCounts(), project);
        recruitmentRepository.saveAll(recruitmentList);

        return projectRepository.save(project);
    }

    public List<Recruitment> getRecruitmentList(Map<MemberPart, Integer> partCount, Project project){
        List<Recruitment> recruitmentList = new ArrayList<>();
        partCount.forEach(
                (memberPart, count) ->{
                    Recruitment recruitment = RecruitmentConverter.toRecruitment(project, memberPart,count);
                    recruitmentList.add(recruitment);
                }
        );
        System.out.println(recruitmentList);
        return recruitmentList;
    }
}
