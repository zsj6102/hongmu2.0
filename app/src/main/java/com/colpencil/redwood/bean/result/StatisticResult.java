package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class StatisticResult implements Serializable {

    private String msg;
    private int comCount;
    private String code;
    private int notesCount;
    private int likeCount;
    private int shareCount;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setComCount(int comCount) {
        this.comCount = comCount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNotesCount(int notesCount) {
        this.notesCount = notesCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getMsg() {
        return msg;
    }

    public int getComCount() {
        return comCount;
    }

    public String getCode() {
        return code;
    }

    public int getNotesCount() {
        return notesCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }
}
