package com.capstone.sjseed.apiPayload.exception.handler;

import com.capstone.sjseed.apiPayload.exception.GeneralException;
import com.capstone.sjseed.apiPayload.form.BaseCode;

public class PlantHandler extends GeneralException {

    private final Object result;

    public PlantHandler(BaseCode code) {
        super(code);
        this.result = null;
    }

    public PlantHandler(BaseCode code, Object result) {
        super(code);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
