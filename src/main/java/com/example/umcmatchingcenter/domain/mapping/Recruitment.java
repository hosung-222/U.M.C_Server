package com.example.umcmatchingcenter.domain.mapping;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.RecruitmentStatus;
import lombok.*;

import javax.persistence.*;

//프로젝트 - 파트 모집

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private MemberPart part;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatus recruitmentStatus;

    @Column(nullable = false)
    private int nowRecruitment;

    @Column(nullable = false)
    private int totalRecruitment;

}
