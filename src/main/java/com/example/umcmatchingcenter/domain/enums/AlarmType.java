package com.example.umcmatchingcenter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    //관리자
    JOIN_NEW("가입", "새로운 챌린저의 가입 신청이 등록되었습니다."),
    MATCHING_COMPLETE("매칭","의 매칭이 완료되었습니다."),
    //챌린저 공통
    NOTICE_NEW("공지", "새로운 공지사항이 등록되었습니다."),

    //PM
    MATCHING_NEW("매칭","새로운 지원이 있습니다."),
    QNA_NEW("매칭","새로운 문의가 있습니다."),
    //PM아님
    MATCHING_APPLY_COMPLETE("매칭", "에 지원이 완료되었습니다."),
    MATCHING_APPLY_FAIL("매칭", "팀과 매칭이 완료되었습니다."),
    MATCHING_APPLY_SUCCESS("매칭","팀과 매칭이 이루어지지 않았습니다.");

    private final String title;
    private final String message;
}
