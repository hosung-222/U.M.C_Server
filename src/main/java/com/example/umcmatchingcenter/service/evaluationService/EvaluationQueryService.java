package com.example.umcmatchingcenter.service.evaluationService;

import com.example.umcmatchingcenter.apiPayload.exception.handler.EvaluationHandler;
import com.example.umcmatchingcenter.apiPayload.exception.handler.ProjectHandler;
import com.example.umcmatchingcenter.domain.Evaluation;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.repository.EvaluationRepository;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.service.projectService.ProjectQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluationQueryService {

    private final EvaluationRepository evaluationRepository;
    private final MemberQueryService memberQueryService;
    private final ProjectQueryService projectQueryService;

    public Evaluation getEvaluation(Member evaluator, Member evaluatee) {
        Optional<Evaluation> evaluation =
                evaluationRepository.findByEvaluatorAndEvaluatee(evaluator, evaluatee);
        if (evaluation.isPresent() && evaluation != null) {
            return evaluation.get();
        }
        return null;
    }

    public Evaluation isExist(Member evaluator, Member evaluatee) {
        Evaluation evaluation = getEvaluation(evaluator, evaluatee);
        if (evaluation != null) {
            return evaluation;
        }
        return null;
    }

    public void contains(Project project, Member evaluatee) {
        if (!project.getMembers().contains(evaluatee)) {
            throw new EvaluationHandler(TEAMMATE_NOT_FOUND);
        }
    }

    public List<Evaluation> getMyEvaluation() {

        Member member = memberQueryService.getCurrentLoginMember();
        List<Evaluation> evaluations = evaluationRepository.findByEvaluatee(member);

        if(member.getProject()==null){
            throw new ProjectHandler(PROJECT_NOT_EXIST);
        }
        if(evaluations.isEmpty()){
            throw new EvaluationHandler(EVALUATION_NOT_FOUND);
        }

        return evaluations;
    }


}
