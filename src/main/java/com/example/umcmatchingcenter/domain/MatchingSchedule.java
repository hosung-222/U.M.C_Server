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

    public static final String CENTURY_PREFIX = "20";
    public static final String DATE_DIVIDER = "-";
    public static final String DATE_PATTERN = "%02d";

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
        this.startDate = combineDate(schedule.getStartYear(), schedule.getStartMonth(), schedule.getStartDay());
        this.endDate = combineDate(schedule.getEndYear(), schedule.getEndMonth(), schedule.getEndDay());
    }

    public static String combineDate(Integer year, Integer month, Integer day) {
        return CENTURY_PREFIX + year + DATE_DIVIDER + String.format(DATE_PATTERN, month) + DATE_DIVIDER + String.format(DATE_PATTERN, day);
    }

    public static Integer splitDate(String str, int i) {
        String[] date = str.split(DATE_DIVIDER);
        String current = date[i];
        current = current.substring(current.length()-2);
        return Integer.parseInt(current);
    }
}
