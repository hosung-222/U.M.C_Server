package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.ProjectDTO.PartMatchingResponseDTO;

public class ProjectConverter {

    public static PartMatchingResponseDTO toPartMatchingResponseDto(MemberPart part, int matchingNum, int totalNum) {
        return PartMatchingResponseDTO.builder()
                .part(part)
                .matchingNum(matchingNum)
                .totalNum(totalNum)
                .build();
    }
}
