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

    @OneToMany(mappedBy = "project")
    private List<Image> images;

    public void setStatus(ProjectStatus projectStatus) {
        this.status = projectStatus;
    }

    public void updateProject(MatchingRequestDTO.UpdateMatchingProjectRequestDTO request){
        this.name = request.getName();
        this.body = request.getBody();
        this.introduction = request.getIntroduction();
    }

    public Image getProfileImage(){
        return this.getImages().stream()
                .filter(Image::isProfile)
                .findFirst()
                .orElse(null);
    }

}
