package com.example.umcmatchingcenter.dto.evaluationDTO;

import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponseDTO {

    private Long memberId;
    private String profileImage;
    private String nameNickname;
    private MemberPart memberPart;
    private String university;
    private double rate;
    private String content;

}
