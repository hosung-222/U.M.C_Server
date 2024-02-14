package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.ProjectDTO.ApplicantInfoResponseDTO;

public class ApplicantsConverter {

    public static ApplicantInfoResponseDTO toProjectApplicantsResponseDto(Member member) {
        return ApplicantInfoResponseDTO.builder()
                .memberId(member.getId())
                .nameNickname(member.getNameNickname())
                .university(member.getUniversity().getName())
                .memberPart(member.getPart())
                .profileImage(member.getProfileImage())
                .portfolio(member.getPortfolio())
                .phoneNumber(member.getPhoneNumber())
                .memberMatchingStatus(member.getMatchingStatus())
                .build();
    }
}
