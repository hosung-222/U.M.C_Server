package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;

import javax.persistence.*;

import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import com.example.umcmatchingcenter.domain.mapping.Application;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nameNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId")
    private University university;

    @Column(nullable = false)
    private String phoneNumber;

    private String portfolio;

    private String profileImage;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    private String memberName;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberPart part;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("NON")
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'NON'")
    private MemberMatchingStatus matchingStatus;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Alarm> alarms;

    @OneToMany(mappedBy = "pm", cascade = CascadeType.ALL)
    private List<Chat> pmChats;

    @OneToMany(mappedBy = "inquirer", cascade = CascadeType.ALL)
    private List<Chat> inquirerChats;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

}