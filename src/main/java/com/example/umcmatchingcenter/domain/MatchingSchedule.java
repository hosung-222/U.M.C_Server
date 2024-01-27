package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.ScheduleColor;
import com.example.umcmatchingcenter.dto.MatchingDTO.MatchingScheduleRequestDTO;
import lombok.*;

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
    @JoinColumn(name = "branch_id")
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

    public void updateSchedule(MatchingScheduleRequestDTO.MatchingScheduleDTO schedule) {
        this.name = schedule.getTitle();
        this.description = schedule.getDescription();
        this.scheduleColor = schedule.getScheduleColor();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
    }
}
