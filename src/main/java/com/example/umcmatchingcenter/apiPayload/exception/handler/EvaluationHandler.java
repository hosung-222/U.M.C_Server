package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class EvaluationHandler extends GeneralException {

    public EvaluationHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
