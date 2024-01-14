package com.example.umcmatchingcenter.dto.projectDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyProjectResponseDto {

    private TotalMatchingResponseDto totalMatchingResponseDto;
    private List<PartMatchingResponseDto> partMatchingDto;
    private double competitionRate;
    private List<ApplicantInfoResponseDto> applicantInfoList;

}
