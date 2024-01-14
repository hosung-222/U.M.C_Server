package com.example.umcmatchingcenter.dto.projectDto;

import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicantInfoResponseDto {

    String nameNickname;
    String university;
    MemberPart memberPart;
    String profileImage;
    MemberMatchingStatus memberMatchingStatus;

}
