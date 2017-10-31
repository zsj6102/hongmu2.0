package com.colpencil.redwood.bean;

import java.io.Serializable;



public class AdInfo implements Serializable {
    private String atturl;//广告图
    private String url;//广告图的链接
    private float cover_proportion;//宽高比
    private String htmlurl;
    private String type;
    private int bannerId;

    public String getAtturl() {
        return atturl;
    }

    public void setAtturl(String atturl) {
        this.atturl = atturl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getCover_proportion() {
        return cover_proportion;
    }

    public void setCover_proportion(float cover_proportion) {
        this.cover_proportion = cover_proportion;
    }
}
