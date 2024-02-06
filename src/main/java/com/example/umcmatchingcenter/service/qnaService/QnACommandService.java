package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface QnACommandService {

    @Transactional
    public abstract QnA postQuestion(QnARequestDTO.questionDTO request, Long projectId, String inquirerName);

    @Transactional
    public abstract void postAnswer(QnARequestDTO.answerDTO request, Long questionId, String respondentName);

    public abstract QnA checkIsAuthorized(Long questionId, Member member);
}
