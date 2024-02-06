package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.QnAHandler;
import com.example.umcmatchingcenter.converter.QnAConverter;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.QnA;
import com.example.umcmatchingcenter.dto.QnADTO.QnARequestDTO;
import com.example.umcmatchingcenter.repository.MatchingRepository;
import com.example.umcmatchingcenter.repository.MemberRepository;
import com.example.umcmatchingcenter.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnACommandServiceImpl implements QnACommandService {

    private final MemberRepository memberRepository;
    private final QnARepository qnaRepository;
    private final MatchingRepository matchingRepository;

    @Override
    public QnA postQuestion(QnARequestDTO.questionDTO request, Long projectId, String memberName) {
        Member inquirer = memberRepository.findByMemberName(memberName)
                .orElseThrow(()-> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Optional<Project> findProject = matchingRepository.findById(projectId);

        if (findProject.isPresent()) {
            Project project = findProject.get();
            QnA newQnA = QnAConverter.toQuestion(request, project, inquirer);
            return qnaRepository.save(newQnA);
        } else {
            throw new ProjectHandler(ErrorStatus.PROJECT_NOT_PROCEEDING);
        }
    }

    @Override
    public void postAnswer(QnARequestDTO.answerDTO request, Long questionId, String respondentName) {
        Member respondent = memberRepository.findByMemberName(respondentName)
                .orElseThrow(()-> new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 답변 권한 확인
        QnA findQuestion = checkIsAuthorized(questionId, respondent);
        // 답변 생성
        findQuestion.updateAnswer(request.getAnswer());
        qnaRepository.save(findQuestion);
    }

    @Override
    public void deleteAnswer(Long questionId, String respondentName) {
        Member respondent = memberRepository.findByMemberName(respondentName)
                .orElseThrow(()-> new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 답변 권한 확인
        QnA findQuestion = checkIsAuthorized(questionId, respondent);
        // 답변 삭제
        findQuestion.deleteAnswer();
        qnaRepository.save(findQuestion);
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
