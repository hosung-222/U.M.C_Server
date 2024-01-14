package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.projectDto.ApplicantInfoResponseDto;

public class ApplicantsConverter {

    public static ApplicantInfoResponseDto toProjectApplicantsResponseDto(String nameNickname,
                                                                          String university,
                                                                          MemberPart memberPart,
                                                                          String profileImage,
                                                                          MemberMatchingStatus memberMatchingStatus) {
        return ApplicantInfoResponseDto.builder()
                .nameNickname(nameNickname)
                .university(university)
                .memberPart(memberPart)
                .profileImage(profileImage)
                .memberMatchingStatus(memberMatchingStatus).build();
    }
}
