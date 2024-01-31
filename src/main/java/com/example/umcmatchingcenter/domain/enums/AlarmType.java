package com.example.umcmatchingcenter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    JOIN("가입"),
    MATCHING("매칭"),
    NOTICE("공지");

    private final String title;
}
