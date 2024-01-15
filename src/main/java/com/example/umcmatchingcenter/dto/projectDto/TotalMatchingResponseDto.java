package com.example.umcmatchingcenter.dto.projectDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalMatchingResponseDto {

    private int nowMatchingNum;
    private int totalMatchingNum;

}
