package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationRequestDTO;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationResponseDTO;

public class EvaluationConverter {

    public static EvaluationResponseDTO toEvaluationResponseDTO(String profileImage,
                                                                String nameNickname,
                                                                MemberPart memberPart,
                                                                String university,
                                                                double rate) {
        return EvaluationResponseDTO.builder()
                .profileImage(profileImage)
                .nameNickname(nameNickname)
                .memberPart(memberPart)
                .university(university)
                .rate(rate)
                .build();

    }

}
