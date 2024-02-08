package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;

import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;

import com.example.umcmatchingcenter.domain.mapping.Recruitment;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingRequestDTO;
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
    private Member pm;

    private String name;

    private String introduction;

    private String image;

    private String body;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'PROCEEDING'")
    private ProjectStatus status;

    @OneToMany(mappedBy = "project")
    private List<Evaluation> evaluations;

    @OneToMany(mappedBy = "project")
    private List<Member> members;

    @OneToMany(mappedBy = "project")
    private List<Recruitment> recruitments;


    @OneToMany(mappedBy = "project")
    private List<ProjectVolunteer> projectVolunteerList;

    public void setStatus(ProjectStatus projectStatus) {
        this.status = projectStatus;
    }

    public void setPm(Member pm){
        this.pm = pm;
    }

    public void setBranch(Branch branch){
        this.branch = branch;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void updateProject(MatchingRequestDTO.UpdateMatchingProjectRequestDto request){
        this.name = request.getName();
        this.body = request.getBody();
        this.introduction = request.getIntroduction();
    }

}
