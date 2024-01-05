package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.ScheduleColor;
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
public class MatchingSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private ScheduleColor scheduleColor;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String name;

    private String description;

}
