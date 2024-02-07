package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class NoticeHandler extends GeneralException {
    public NoticeHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
