package com.example.umcmatchingcenter.dto.QnADTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class QnAResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class questionResultDTO {
        private Long questionId;
    }

}
