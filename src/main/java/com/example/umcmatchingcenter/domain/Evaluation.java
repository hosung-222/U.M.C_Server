package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Evaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluatorId")
    private Member evaluator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluateeId")
    private Member evaluatee;

    @Column(nullable = false)
    private int rate;

    @Column(nullable = false)
    private String content;
}
