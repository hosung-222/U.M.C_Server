package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.QnAHandler;
import com.example.umcmatchingcenter.converter.QnAConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.dto.qnaDTO.QnARequestDTO;
import com.example.umcmatchingcenter.repository.MatchingRepository;
import com.example.umcmatchingcenter.repository.QnARepository;
import com.example.umcmatchingcenter.service.AlarmService.AlarmCommandService;
import com.example.umcmatchingcenter.service.matchingService.MatchingQueryService;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnACommandServiceImpl implements QnACommandService {

    private final QnARepository qnaRepository;

    private final MemberQueryService memberQueryService;
    private final MatchingQueryService matchingQueryService;
    private final AlarmCommandService alarmCommandService;

    @Override
    public QnA postQuestion(QnARequestDTO.questionDTO request, Long projectId, String memberName) {
        Member inquirer = memberQueryService.findMemberByName(memberName);
        Project findProject = matchingQueryService.findProcedingProject(projectId);

        QnA newQnA = QnAConverter.toQuestion(request, findProject, inquirer);
        alarmCommandService.send(findProject.getPm(), AlarmType.QNA_NEW,findProject.getId().toString()+"+"+AlarmType.QNA_NEW.getMessage());
        return qnaRepository.save(newQnA);
    }

    @Override
    public void postAnswer(QnARequestDTO.answerDTO request, Long questionId, String respondentName) {
        Member respondent = memberQueryService.findMemberByName(respondentName);
        // 답변 권한 확인
        QnA findQuestion = checkIsAuthorized(questionId, respondent);
        // 답변 생성
        findQuestion.updateAnswer(request.getAnswer());
        qnaRepository.save(findQuestion);
    }

    @Override
    public void deleteQuestion(Long questionId, String respondentName) {
        Member respondent = memberQueryService.findMemberByName(respondentName);
        // 답변 권한 확인
        checkIsAuthorized(questionId, respondent);
        // Q&A 질문 삭제
        qnaRepository.deleteById(questionId);
    }

    @Override
    public QnA checkIsAuthorized(Long questionId, Member member) {
        Optional<QnA> findOptionalQuestion = qnaRepository.findById(questionId);

        // 질문 존재 여부 확인
        if (findOptionalQuestion.isPresent()) {

            QnA question = findOptionalQuestion.get();

            // 답변 권한 확인
            if (!member.equals(question.getProject().getPm())) {
                throw new QnAHandler(ErrorStatus.ANSWER_UNAUTHORIZED);
            }
            return question;

        } else {
            throw new QnAHandler(ErrorStatus.QUESTION_NOT_FOUNT);
        }
    }
}
