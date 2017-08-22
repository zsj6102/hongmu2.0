package com.colpencil.redwood.bean;

import java.io.Serializable;

public class UrlString implements Serializable{
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
