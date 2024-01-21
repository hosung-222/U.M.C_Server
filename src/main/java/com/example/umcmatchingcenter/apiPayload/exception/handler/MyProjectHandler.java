package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class MyProjectHandler extends GeneralException {

    public MyProjectHandler(BaseErrorCode code) {
        super(code);
    }
}
