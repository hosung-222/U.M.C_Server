package com.example.umcmatchingcenter.service.applyService;

import com.example.umcmatchingcenter.converter.ProjectVolunteerConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.repository.ProjectVolunteerRepository;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.matchingService.MatchingQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplyService {

    private final MatchingQueryService matchingQueryService;
    private final ProjectVolunteerRepository projectVolunteerRepository;
    private final MemberQueryService memberQueryService;
    private final ApplyQueryService applyQueryService;
    private final AlarmCommandService alarmCommandService;

    public void apply(Long projectId) {
        Member member = memberQueryService.getCurrentLoginMember();
        Project project = matchingQueryService.findProject(projectId);

        applyQueryService.isFull(projectId);
        applyQueryService.alreadyApply();
        applyQueryService.isSameGeneration(projectId);

        member.setMatchingStatus(MemberMatchingStatus.APPLY);
        projectVolunteerRepository.save(ProjectVolunteerConverter.toProjectVolunteer(project, member));

        alarmCommandService.send(member,
                AlarmType.MATCHING_APPLY_COMPLETE,
                project.getId().toString()+"+"+member.getNameNickname()+AlarmType.MATCHING_APPLY_COMPLETE.getMessage());

        Member manager = memberQueryService.getManager(member);
        alarmCommandService.send(manager,
                AlarmType.MATCHING_COMPLETE,
                member.getNameNickname()+AlarmType.MATCHING_COMPLETE.getMessage());
    }


}
