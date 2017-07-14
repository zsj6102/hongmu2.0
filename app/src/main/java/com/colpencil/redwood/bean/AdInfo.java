package com.colpencil.redwood.bean;

import java.io.Serializable;

public class AdInfo implements Serializable {
    private String atturl;
    private String url;

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
}
