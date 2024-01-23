package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;


public class ProjectHandler extends GeneralException {
    public ProjectHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
