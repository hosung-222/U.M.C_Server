package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.AlarmColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MatchingSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private AlarmColor alarmColor;

    @Column(nullable = false)
    private String start_date;

    @Column(nullable = false)
    private String end_date;

    @Column(nullable = false)
    private String name;

    private String description;

}
