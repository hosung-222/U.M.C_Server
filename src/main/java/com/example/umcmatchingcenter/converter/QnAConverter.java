package com.example.umcmatchingcenter.converter;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import com.example.umcmatchingcenter.dto.QnADTO.QnAResponseDTO;

import java.util.Optional;

public class QnAConverter {
    // Q&A 생성
    public static QnAResponseDTO.QnAResultDTO toPostQnAResultDTO(QnA qna) {
        return QnAResponseDTO.QnAResultDTO.builder()
                .questionId(qna.getId())
                .build();
    }

    public static QnA toQnA(QnARequestDTO.QnADTO request, Project project) {

        return QnA.builder()
                .project(project)
//                .pm(project.getPm())
                .question(request.getQuestion())
                .build();
    }
}
