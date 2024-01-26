package com.example.umcmatchingcenter.converter.myProject;

import com.example.umcmatchingcenter.dto.ProjectDTO.ApplicantInfoResponseDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.MyProjectResponseDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.PartMatchingResponseDTO;
import com.example.umcmatchingcenter.dto.ProjectDTO.TotalMatchingResponseDTO;

import java.util.List;

public class MyProjectConverter {

    public static MyProjectResponseDTO toMyProjectResponseDto(TotalMatchingResponseDTO totalMatchingResponseDTO,
                                                              List<PartMatchingResponseDTO> partMatchingDTO,
                                                              double competitionRate,
                                                              List<ApplicantInfoResponseDTO> applicantInfoDTO) {

        return MyProjectResponseDTO.builder()
                .totalMatchingResponseDTO(totalMatchingResponseDTO)
                .partMatchingDTO(partMatchingDTO)
                .competitionRate(competitionRate)
                .applicantInfoList(applicantInfoDTO)
                .build();
    }
}
