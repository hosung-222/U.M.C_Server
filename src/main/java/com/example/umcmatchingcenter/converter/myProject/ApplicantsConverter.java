package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.ProjectDTO.ApplicantInfoResponseDTO;

public class ApplicantsConverter {

    public static ApplicantInfoResponseDTO toProjectApplicantsResponseDto(String nameNickname,
                                                                          String university,
                                                                          MemberPart memberPart,
                                                                          String profileImage,
                                                                          MemberMatchingStatus memberMatchingStatus) {
        return ApplicantInfoResponseDTO.builder()
                .nameNickname(nameNickname)
                .university(university)
                .memberPart(memberPart)
                .profileImage(profileImage)
                .memberMatchingStatus(memberMatchingStatus).build();
    }
}
