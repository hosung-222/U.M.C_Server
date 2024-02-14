package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class ApplyHandler extends GeneralException {

    public ApplyHandler(BaseErrorCode code) {
        super(code);
    }
}
