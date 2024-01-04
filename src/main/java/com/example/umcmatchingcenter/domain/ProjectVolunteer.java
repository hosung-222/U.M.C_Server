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
public class ProjectVolunteer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private int round;
}
