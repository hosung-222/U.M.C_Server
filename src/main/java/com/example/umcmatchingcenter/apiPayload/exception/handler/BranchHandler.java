package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class BranchHandler extends GeneralException {
    public BranchHandler(BaseErrorCode code) {
        super(code);
    }
}
