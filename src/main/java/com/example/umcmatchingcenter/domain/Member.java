package com.example.umcmatchingcenter.domain;

import com.example.umcmatchingcenter.domain.common.BaseEntity;
import com.example.umcmatchingcenter.domain.enums.MatchingStatus;
import com.example.umcmatchingcenter.domain.enums.MemberStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import com.example.umcmatchingcenter.domain.enums.Part;
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

    @Column(nullable = false, length = 20)
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false, length = 5)
    private String name;

    @Column(nullable = false, length = 10)
    private String nickName;

    @Column(nullable = false)
    private Long universityId;

    private Boolean isManager;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Part part;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 50)
    private String portfolio;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'INCOMPLETE'")
    private MatchingStatus matchingStatus;

    @Column(length = 50)
    private String profileImgUrl;

    @Column(nullable = false)
    private int generation;

    @Column(length = 50)
    private String userName;

    private Long projectId;

    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'USER'")
    private String authority;



}
