package com.colpencil.redwood.bean;

import java.io.Serializable;

public class ImageSpan implements Serializable {
    private float bl;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getBl() {
        return bl;
    }

    public void setBl(float bl) {
        this.bl = bl;
    }
}
