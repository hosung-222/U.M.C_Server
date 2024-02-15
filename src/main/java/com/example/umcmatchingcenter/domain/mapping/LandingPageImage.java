package com.example.umcmatchingcenter.domain.mapping;

import com.example.umcmatchingcenter.domain.Image;
import com.example.umcmatchingcenter.domain.LandingPage;
import com.example.umcmatchingcenter.domain.Project;
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
public class LandingPageImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "landingPageId")
    private LandingPage landingPage;

    private boolean isProfile;

    public void setProfile(){this.isProfile = true;}
}
