package com.example.umcmatchingcenter.dto.QnADTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class QnARequestDTO {
    @Getter
    public static class QnADTO {

        @NotNull
        @Schema(description = "질문 내용", example = "질문 내용")
        String question;

    }
}
