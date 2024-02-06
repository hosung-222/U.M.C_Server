package com.example.umcmatchingcenter.service.qnaService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.AlarmHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
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
                .orElseThrow(()-> new ProjectHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Optional<Project> findProject = matchingRepository.findById(projectId);

        if (findProject.isPresent()) {
            Project project = findProject.get();
            QnA newQnA = QnAConverter.toQuestion(request, project, inquirer);
            return qnaRepository.save(newQnA);
        } else {
            throw new ProjectHandler(ErrorStatus.PROJECT_NOT_PROCEEDING);
        }
    }
}
