package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.dto.projectDto.ApplicantInfoResponseDto;
import com.example.umcmatchingcenter.dto.projectDto.MyProjectResponseDto;
import com.example.umcmatchingcenter.dto.projectDto.PartMatchingResponseDto;
import com.example.umcmatchingcenter.dto.projectDto.TotalMatchingResponseDto;

import java.util.List;

public class MyProjectConverter {

    public static MyProjectResponseDto toMyProjectResponseDto(TotalMatchingResponseDto totalMatchingResponseDto,
                                                              List<PartMatchingResponseDto> partMatchingDto,
                                                              double competitionRate,
                                                              List<ApplicantInfoResponseDto> applicantInfoDto) {

        return MyProjectResponseDto.builder()
                .totalMatchingResponseDto(totalMatchingResponseDto)
                .partMatchingDto(partMatchingDto)
                .competitionRate(competitionRate)
                .applicantInfoList(applicantInfoDto)
                .build();
    }
}
