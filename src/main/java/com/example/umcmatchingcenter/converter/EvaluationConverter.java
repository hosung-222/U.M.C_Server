package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationRequestDTO;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationResponseDTO;

public class EvaluationConverter {

    public static EvaluationResponseDTO toEvaluationResponseDTO(Member member,
                                                                double rate,
                                                                String content) {
        return EvaluationResponseDTO.builder()
                .profileImage(member.getProfileImage())
                .nameNickname(member.getNameNickname())
                .memberPart(member.getPart())
                .university(member.getUniversity().getName())
                .rate(rate)
                .content(content)
                .build();

    }

}
