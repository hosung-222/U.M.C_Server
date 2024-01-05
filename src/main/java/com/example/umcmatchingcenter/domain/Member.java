package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MemberMatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberPart;
import com.example.umcmatchingcenter.domain.enums.MemberRole;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
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

   // @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "universityId")
   // private University university;

    @Column(nullable = false)
    private String phoneNumber;

    private String portfolio;

    private String profileImage;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'CHALLENGER'")
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberPart part;

    @Enumerated(EnumType.STRING)
    private MemberMatchingStatus matchingStatus;

    //@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    //private List<Alarm> alarms = new ArrayList<>();

    //@OneToMany(mappedBy = "pm", cascade = CascadeType.ALL)
    //private List<Chat> pmChats = new ArrayList<>();

    //@OneToMany(mappedBy = "inquirer", cascade = CascadeType.ALL)
    //private List<Chat> inquirerChats = new ArrayList<>();

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "projectId")
    //private Project project;


}
