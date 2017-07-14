package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.GoodInfo;

import java.io.Serializable;

public class GoodInfoResult implements Serializable {

    private String code;
    private String msg;
    private GoodInfo goodInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GoodInfo getGoodInfo() {
        return goodInfo;
    }

    public void setGoodInfo(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }
}
