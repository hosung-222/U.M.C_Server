package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class MatchingHandler extends GeneralException {
    public MatchingHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
