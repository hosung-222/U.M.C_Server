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

    @ManyToOne
    @JoinColumn(name = "pm_id")
    private Member pm;

    @ManyToOne
    @JoinColumn(name = "inquirer_id")
    private Member inquirer;

    @Column(nullable = false)
    private String body;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_confirmed;

}
