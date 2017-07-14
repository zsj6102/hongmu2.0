package com.colpencil.redwood.bean.result;

import java.io.Serializable;
import java.util.List;

public class PostsResult implements Serializable {

    private String face;
    private String ote_title;
    private String ote_content;
    private String nickname;
    private long creattime;
    private String code;
    private String msg;
    private String url;
    private int like_count;
    private int com_count;
    private long systemTime;
    private List<String> ote_images;
    private int ote_sharenum;
    private int ote_stick;
    private int ote_digest;
    private String member_photo;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getOte_title() {
        return ote_title;
    }

    public void setOte_title(String ote_title) {
        this.ote_title = ote_title;
    }

    public String getOte_content() {
        return ote_content;
    }

    public void setOte_content(String ote_content) {
        this.ote_content = ote_content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getCreattime() {
        return creattime;
    }

    public void setCreattime(long creattime) {
        this.creattime = creattime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getOte_images() {
        return ote_images;
    }

    public void setOte_images(List<String> ote_images) {
        this.ote_images = ote_images;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getCom_count() {
        return com_count;
    }

    public void setCom_count(int com_count) {
        this.com_count = com_count;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public int getOte_sharenum() {
        return ote_sharenum;
    }

    public void setOte_sharenum(int ote_sharenum) {
        this.ote_sharenum = ote_sharenum;
    }

    public int getOte_stick() {
        return ote_stick;
    }

    public void setOte_stick(int ote_stick) {
        this.ote_stick = ote_stick;
    }

    public int getOte_digest() {
        return ote_digest;
    }

    public void setOte_digest(int ote_digest) {
        this.ote_digest = ote_digest;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }
}
