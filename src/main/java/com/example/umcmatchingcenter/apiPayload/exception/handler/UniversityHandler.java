package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class UniversityHandler extends GeneralException {
    public UniversityHandler(BaseErrorCode code) {
        super(code);
    }
}
