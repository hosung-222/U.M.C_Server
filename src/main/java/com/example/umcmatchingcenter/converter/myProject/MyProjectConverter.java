package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.dto.ProjectDTO.ApplicantInfoResponseDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectResponseDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.PartMatchingResponseDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.TotalMatchingResponseDTO;

import java.util.List;

public class MyProjectConverter {

    public static MyProjectResponseDTO toMyProjectResponseDto(TotalMatchingResponseDTO totalMatchingResponseDto,
                                                              List<PartMatchingResponseDTO> partMatchingDto,
                                                              double competitionRate,
                                                              List<ApplicantInfoResponseDTO> applicantInfoDto) {

        return MyProjectResponseDTO.builder()
                .totalMatchingResponseDto(totalMatchingResponseDto)
                .partMatchingDto(partMatchingDto)
                .competitionRate(competitionRate)
                .applicantInfoList(applicantInfoDto)
                .build();
    }
}
