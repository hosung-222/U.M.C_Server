package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.AlarmType;
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
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_confirmed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType alarmType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

}
