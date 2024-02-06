package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface QnACommandService {

    @Transactional
    public abstract QnA postQuestion(QnARequestDTO.questionDTO request, Long projectId);
}
