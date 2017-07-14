package com.colpencil.redwood.bean;

import java.io.Serializable;

public class BidderInfoVo implements Serializable {

    private String face;
    private String name;
    private float offer;
    private int results;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }
}
