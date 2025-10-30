package com.capstone.sjseed.apiPayload.exception.handler;

import com.capstone.sjseed.apiPayload.exception.GeneralException;
import com.capstone.sjseed.apiPayload.form.BaseCode;

public class PieceHandler extends GeneralException
{
    private final Object result;

    public PieceHandler(BaseCode code) {
        super(code);
        this.result = null;
    }

    public PieceHandler(BaseCode code, Object result) {
        super(code);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
