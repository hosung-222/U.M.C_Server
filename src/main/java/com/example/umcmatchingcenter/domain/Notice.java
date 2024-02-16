package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.mapping.LandingPageImage;
import com.example.umcmatchingcenter.domain.mapping.NoticeImage;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @OneToMany(mappedBy = "notice")
    private List<NoticeImage> images;

    public void setBranch(Branch branch){this.branch = branch;}

    public void update(String title, String body){
        this.title = title;
        this.body = body;
    }

}