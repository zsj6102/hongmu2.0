package com.colpencil.redwood.bean;

import java.io.Serializable;

public class Payinfo implements Serializable {
    private int type;
    /**
     * 支付返回参数
     */
    private PayResult result;
    private String tn;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PayResult getResult() {
        return result;
    }

    public void setResult(PayResult result) {
        this.result = result;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}
