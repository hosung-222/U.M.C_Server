package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Branch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "branch")
    private List<University> universities;

    @OneToMany(mappedBy = "branch")
    private List<Project> projects;

    @OneToMany(mappedBy = "branch")
    private List<Notice> notices;

    @OneToMany(mappedBy = "branch")
    private List<MatchingSchedule> schedules;

}
