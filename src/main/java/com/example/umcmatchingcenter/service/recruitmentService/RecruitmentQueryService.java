package com.example.umcmatchingcenter.service.recruitmentService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.RecruitmentHandler;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.RecruitmentRepository;
import com.example.umcmatchingcenter.service.branchService.BranchQueryService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentQueryService {
    private final RecruitmentRepository recruitmentRepository;

    public Recruitment getRecruitment(MemberPart part, Project project) {
        Optional<Recruitment> byPartAndProject = recruitmentRepository.findByPartAndProject(part, project);
        if (byPartAndProject.isEmpty()){
            throw new RecruitmentHandler(ErrorStatus.RECRUITMENT_NOT_FOUNT);
        }
        return byPartAndProject.get();
    }

}
