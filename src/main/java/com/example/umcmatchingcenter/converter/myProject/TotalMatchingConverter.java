package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.dto.projectDTO.TotalMatchingResponseDTO;

public class TotalMatchingConverter {

    public static TotalMatchingResponseDTO toTotalMatchingResponseDTO(int nowMatchingNum,
                                                                      int totalMatchingNum) {
        return TotalMatchingResponseDTO.builder()
                .nowMatchingNum(nowMatchingNum)
                .totalMatchingNum(totalMatchingNum)
                .build();
    }
}
