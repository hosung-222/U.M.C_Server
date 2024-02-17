package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationRequestDTO;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationResponseDTO;
import com.example.umcmatchingcenter.dto.evaluationDTO.MyEvaluationResponseDTO;

public class EvaluationConverter {

    public static EvaluationResponseDTO toEvaluationResponseDTO(Member member,
                                                                double rate,
                                                                String content) {
        return EvaluationResponseDTO.builder()
                .memberId(member.getId())
                .profileImage(member.getProfileImage())
                .nameNickname(member.getNameNickname())
                .memberPart(member.getPart())
                .university(member.getUniversity().getName())
                .rate(rate)
                .content(content)
                .build();

    }

    public static MyEvaluationResponseDTO toMyEvaluationResponseDTO(double rate, String content) {
        return MyEvaluationResponseDTO.builder()
                .rate(rate)
                .content(content)
                .build();
    }



}
