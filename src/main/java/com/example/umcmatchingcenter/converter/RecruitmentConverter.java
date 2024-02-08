package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;

public class RecruitmentConverter {

    public static Recruitment toRecruitment(Project project, MemberPart part, int totalRecruitment){
        return Recruitment.builder()
                .project(project)
                .part(part)
                .totalRecruitment(totalRecruitment)
                .nowRecruitment(0)
                .build();
    }
}
