package com.capstone.sjseed.apiPayload.exception.handler;

import com.capstone.sjseed.apiPayload.exception.GeneralException;
import com.capstone.sjseed.apiPayload.form.BaseCode;

public class MemberHandler extends GeneralException {

    private final Object result;

    public MemberHandler(BaseCode code) {
        super(code);
        this.result = null;
    }

    public MemberHandler(BaseCode code, Object result) {
        super(code);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
