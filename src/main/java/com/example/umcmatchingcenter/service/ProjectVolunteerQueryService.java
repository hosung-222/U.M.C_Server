package com.example.umcmatchingcenter.service;


import com.example.umcmatchingcenter.converter.ProjectVolunteerConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;
import com.example.umcmatchingcenter.repository.ProjectVolunteerRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectVolunteerQueryService {

    private final ProjectVolunteerRepository projectVolunteerRepository;

    public List<ApplyTeamDTO> getAllApplyTeam(Member member){
        List<ProjectVolunteer> matchingScheduleList = projectVolunteerRepository.findAllByMember(member);

        return matchingScheduleList.stream()
                .map(ProjectVolunteerConverter::toApplyTeamDTO)
                .collect(Collectors.toList());
    }
}
