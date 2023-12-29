package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
