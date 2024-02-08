package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Evaluation;
import com.example.umcmatchingcenter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    Optional<Evaluation> findByEvaluatorAndEvaluatee(Member Evaluator, Member Evaluatee);
}
