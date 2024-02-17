package com.example.umcmatchingcenter.dto.evaluationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyEvaluationResponseDTO {

    private double rate;
    private String content;
}
