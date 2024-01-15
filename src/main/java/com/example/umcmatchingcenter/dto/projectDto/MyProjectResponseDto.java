package com.example.umcmatchingcenter.dto.projectDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyProjectResponseDto {

    private TotalMatchingResponseDto totalMatchingResponseDto;
    private List<PartMatchingResponseDto> partMatchingDto;
    private double competitionRate;
    private List<ApplicantInfoResponseDto> applicantInfoList;

}
