package com.example.umcmatchingcenter.service.evaluationService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.EvaluationHandler;
import com.example.umcmatchingcenter.converter.EvaluationConverter;
import com.example.umcmatchingcenter.domain.Evaluation;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationRequestDTO;
import com.example.umcmatchingcenter.dto.evaluationDTO.EvaluationResponseDTO;
import com.example.umcmatchingcenter.repository.EvaluationRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluationService {

    private final MemberQueryService memberQueryService;
    private final EvaluationQueryService evaluationQueryService;
    private final EvaluationRepository evaluationRepository;

    public List<EvaluationResponseDTO> getTeammates() {
        Member currentLoginMember = memberQueryService.getCurrentLoginMember();
        List<Member> members = currentLoginMember.getProject().getMembers();
        members.removeIf(member -> member.equals(currentLoginMember));

        return members.stream()
                .map(member -> {
                    Evaluation evaluation = evaluationQueryService.getEvaluation(currentLoginMember, member);
                    if(evaluation == null){
                        return EvaluationConverter.toEvaluationResponseDTO(member,0.0,null);
                    }
                    return EvaluationConverter.toEvaluationResponseDTO(member,evaluation.getRate(),evaluation.getContent());
                })
                .collect(Collectors.toList());
    }

    public void saveEvaluation(EvaluationRequestDTO dto, Long memberId) {

        Member evaluator = memberQueryService.getCurrentLoginMember();
        Member evaluatee = memberQueryService.findMember(memberId);
        Project project = evaluator.getProject();

        evaluationQueryService.isExist(evaluator, evaluatee);
        evaluationQueryService.contains(project, evaluatee);

        evaluationRepository.save(Evaluation.builder()
                .project(project)
                .evaluator(evaluator)
                .evaluatee(evaluatee)
                .content(dto.getContent())
                .rate(dto.getRate())
                .build());
    }

}
