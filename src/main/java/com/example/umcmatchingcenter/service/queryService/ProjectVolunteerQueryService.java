package com.example.umcmatchingcenter.service.queryService;

import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.repository.project.ProjectVolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectVolunteerQueryService {

    private final ProjectVolunteerRepository projectVolunteerRepository;

    public Optional<ProjectVolunteer> getProjectVolunteer(Long memberId) {
        Optional<ProjectVolunteer> foundApplication = projectVolunteerRepository.findByMemberId(memberId);
        return foundApplication;
    }
}
