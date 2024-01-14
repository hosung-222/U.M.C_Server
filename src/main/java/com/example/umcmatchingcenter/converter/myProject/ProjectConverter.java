package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.projectDto.PartMatchingResponseDto;

public class ProjectConverter {

    public static PartMatchingResponseDto toPartMatchingResponseDto(MemberPart part, int matchingNum, int totalNum) {
        return PartMatchingResponseDto.builder()
                .part(part)
                .matchingNum(matchingNum)
                .totalNum(totalNum)
                .build();
    }
}
