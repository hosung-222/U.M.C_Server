package com.example.umcmatchingcenter.service.myProjectService;

import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.repository.ProjectVolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyProjectVolunteerQueryService {

    private final ProjectVolunteerRepository projectVolunteerRepository;

    public Optional<ProjectVolunteer> getProjectVolunteer(Long memberId) {
        Optional<ProjectVolunteer> foundApplication = projectVolunteerRepository.findByMemberId(memberId);
        return foundApplication;
    }
}
