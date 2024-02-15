package com.example.umcmatchingcenter.apiPayload.exception.handler;

import com.example.umcmatchingcenter.apiPayload.code.BaseErrorCode;
import com.example.umcmatchingcenter.apiPayload.exception.GeneralException;

public class ImageHandler extends GeneralException {
        public ImageHandler(BaseErrorCode errorCode){
            super(errorCode);
        }
}
