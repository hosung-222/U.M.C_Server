package com.example.umcmatchingcenter.converter;


import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import com.example.umcmatchingcenter.dto.MemberDTO.MemberResponseDTO;

public class ProjectVolunteerConverter {

    public static MemberResponseDTO.ApplyTeamDTO toApplyTeamDTO(ProjectVolunteer projectVolunteer){
        return MemberResponseDTO.ApplyTeamDTO.builder()
                .round(projectVolunteer.getRound())
                .teamName(projectVolunteer.getProject().getName())
                .build();
    }
}
