package com.colpencil.redwood.bean;

import java.io.Serializable;

public class AddResult implements Serializable {
    private int code; //0：评论成功，1：系统异常，2：评论失败
    private String message; //操作结果信息

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
}
