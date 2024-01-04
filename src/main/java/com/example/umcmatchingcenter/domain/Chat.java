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
public class Chat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pmId")
    private Member pm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquirerId")
    private Member inquirer;

    @Column(nullable = false)
    private String body;

    @Column(columnDefinition = "boolean default false")
    private Boolean isConfirmed;

}
