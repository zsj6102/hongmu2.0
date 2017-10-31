package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CodeBean implements Serializable {

    private String weixinImg;
    private String iosImg;
    private String anroidImg;
    private String content;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeixinImg() {
        return weixinImg;
    }

    public void setWeixinImg(String weixinImg) {
        this.weixinImg = weixinImg;
    }

    public String getIosImg() {
        return iosImg;
    }

    public void setIosImg(String iosImg) {
        this.iosImg = iosImg;
    }

    public String getAnroidImg() {
        return anroidImg;
    }

    public void setAnroidImg(String anroidImg) {
        this.anroidImg = anroidImg;
    }
}
