package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.mapping.Application;
import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id")
    private Member member;

    private String name;

    private String introduction;

    private String image;

    private String body;

    @OneToMany(mappedBy = "project")
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "project")
    private List<Recruitment> recruitments;

    @OneToMany(mappedBy = "project")
    private List<Application> applications;

}
