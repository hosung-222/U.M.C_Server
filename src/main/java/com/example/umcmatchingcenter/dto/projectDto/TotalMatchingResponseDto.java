package com.example.umcmatchingcenter.dto.projectDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalMatchingResponseDto {

    private int nowMatchingNum;
    private int totalMatchingNum;

}
