package com.example.umcmatchingcenter.service.evaluationService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.EvaluationHandler;
import com.example.umcmatchingcenter.domain.Evaluation;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluationQueryService {

    private final EvaluationRepository evaluationRepository;

    public Evaluation getEvaluation(Member evaluator, Member evaluatee) {
        Optional<Evaluation> evaluation =
                evaluationRepository.findByEvaluatorAndEvaluatee(evaluator, evaluatee);

        if (evaluation.isPresent() && evaluation != null) {
            return evaluation.get();
        }
        return null;
    }

    public void isExist(Member evaluator, Member evaluatee) {
        Evaluation evaluation = getEvaluation(evaluator, evaluatee);
        if (evaluation != null) {
            throw new EvaluationHandler(ErrorStatus.ALREADY_EVALUATED_MEMBER);
        }
    }


}
