package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class Pays implements Serializable {
    private int payId;
    private String payImgPath;
    private String payName;
    private String type;

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getPayImgPath() {
        return payImgPath;
    }

    public void setPayImgPath(String payImgPath) {
        this.payImgPath = payImgPath;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
