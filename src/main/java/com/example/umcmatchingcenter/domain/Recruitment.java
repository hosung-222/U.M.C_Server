package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//프로젝트 - 파트 모집

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private MemberPart part;

    @Column(nullable = false)
    private int now_recruitment;

    @Column(nullable = false)
    private int total_recruitment;
}
