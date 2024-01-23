package com.example.umcmatchingcenter.service.queryService;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentQueryService {

    private final RecruitmentRepository recruitmentRepository;

    public Recruitment getRecruitment(MemberPart part, Project project) {
        Optional<Recruitment> foundRecruitment = recruitmentRepository.findByPartAndProject(part,project);
        if (foundRecruitment.isPresent() && foundRecruitment != null) {
            return foundRecruitment.get();
        }
        return null;
    }
}