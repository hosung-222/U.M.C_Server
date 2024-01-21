package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.dto.projectDTO.ApplicantInfoResponseDTO;
import com.example.umcmatchingcenter.dto.projectDTO.MyProjectResponseDTO;
import com.example.umcmatchingcenter.dto.projectDTO.PartMatchingResponseDTO;
import com.example.umcmatchingcenter.dto.projectDTO.TotalMatchingResponseDTO;

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
