package com.example.umcmatchingcenter.dto.projectDto;

import com.example.umcmatchingcenter.domain.University;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantInfoResponseDto {

    private String nameNickname;
    private String university;
    private MemberPart memberPart;
    private String profileImage;
    private MemberMatchingStatus memberMatchingStatus;

}
