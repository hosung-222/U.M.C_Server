package com.example.umcmatchingcenter.converter;


import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO.ApplyTeamDTO;

public class ProjectVolunteerConverter {

    public static ApplyTeamDTO toApplyTeamDTO(ProjectVolunteer projectVolunteer){
        return ApplyTeamDTO.builder()
                .round(projectVolunteer.getRound())
                .teamName(projectVolunteer.getProject().getName())
                .build();
    }
}
