package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class Postages implements Serializable {
    private int postageId;
    private String postageName;
    private double postagePrice;
    private String postageImgPath;

    public String getPostageImgPath() {
        return postageImgPath;
    }

    public void setPostageImgPath(String postageImgPath) {
        this.postageImgPath = postageImgPath;
    }

    public int getPostageId() {
        return postageId;
    }

    public void setPostageId(int postageId) {
        this.postageId = postageId;
    }

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public double getPostagePrice() {
        return postagePrice;
    }

    public void setPostagePrice(double postagePrice) {
        this.postagePrice = postagePrice;
    }
}
