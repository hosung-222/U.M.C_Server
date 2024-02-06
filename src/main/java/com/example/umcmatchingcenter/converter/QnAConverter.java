package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import com.example.umcmatchingcenter.dto.QnADTO.QnAResponseDTO;

public class QnAConverter {
    // Q&A 생성 (질문하기)
    public static QnAResponseDTO.questionResultDTO toPostQuestionResultDTO(QnA qna) {
        return QnAResponseDTO.questionResultDTO.builder()
                .questionId(qna.getId())
                .build();
    }

    public static QnA toQuestion(QnARequestDTO.questionDTO request, Project project) {

        return QnA.builder()
                .project(project)
//                .pm(project.getPm())
                .question(request.getQuestion())
                .build();
    }
}
