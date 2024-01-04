package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;

import javax.persistence.*;

import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private String name_nickname;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @Column(nullable = false)
    private String phoneNumber;

    private String portfolio;

    private String profileImage;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberPart part;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberMatchingStatus matchingStatus;

    @OneToMany(mappedBy = "member_id", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "pm", cascade = CascadeType.ALL)
    private List<Chat> pmChats = new ArrayList<>();

    @OneToMany(mappedBy = "inquirer", cascade = CascadeType.ALL)
    private List<Chat> inquirerChats = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
