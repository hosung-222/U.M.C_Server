package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.dto.projectDto.TotalMatchingResponseDto;

public class TotalMatchingConverter {

    public static TotalMatchingResponseDto toTotalMatchingResponseDto(int nowMatchingNum,
                                                                      int totalMatchingNum) {
        return TotalMatchingResponseDto.builder()
                .nowMatchingNum(nowMatchingNum)
                .totalMatchingNum(totalMatchingNum)
                .build();
    }
}
