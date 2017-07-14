package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.SpecialIntroduceInfo;

import java.io.Serializable;

public class SpecialIntroduceResult implements Serializable {

    private int code;
    private String message;

    private SpecialIntroduceInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SpecialIntroduceInfo getData() {
        return data;
    }

    public void setData(SpecialIntroduceInfo data) {
        this.data = data;
    }
}
