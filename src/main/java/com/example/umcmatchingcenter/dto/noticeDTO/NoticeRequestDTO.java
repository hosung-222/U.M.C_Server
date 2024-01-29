package com.example.umcmatchingcenter.dto.noticeDTO;

import lombok.Getter;
import lombok.Setter;

public class NoticeRequestDTO {
    @Getter
    @Setter
    public static class AddNoticeDTO {
        private String title;
        private String body;
    }

    @Getter
    @Setter
    public static class UpdateNoticeDTO {
        private String title;
        private String body;
    }
}
