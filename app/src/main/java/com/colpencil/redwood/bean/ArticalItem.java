package com.colpencil.redwood.bean;

import java.io.Serializable;

public class ArticalItem implements Serializable {
    private int h_id;//百科新闻id
    private String h_title;//百科新闻标题
    private int con_count;//评论数量
    private int like_count;//点击数量
    private int share_count;//分享数量
    private int is_top;//是否置顶 0:不置顶 1：置顶   以下相似
    private int is_digest;//是否加精
    private int is_commend;//是否推荐
    private String create_time;
    private String now_time;
    private String h_img;//百科新闻显示图片

    public int getH_id() {
        return h_id;
    }

    public void setH_id(int h_id) {
        this.h_id = h_id;
    }

    public String getH_title() {
        return h_title;
    }

    public void setH_title(String h_title) {
        this.h_title = h_title;
    }

    public int getCon_count() {
        return con_count;
    }

    public void setCon_count(int con_count) {
        this.con_count = con_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getIs_digest() {
        return is_digest;
    }

    public void setIs_digest(int is_digest) {
        this.is_digest = is_digest;
    }

    public int getIs_commend() {
        return is_commend;
    }

    public void setIs_commend(int is_commend) {
        this.is_commend = is_commend;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getNow_time() {
        return now_time;
    }

    public void setNow_time(String now_time) {
        this.now_time = now_time;
    }

    public String getH_img() {
        return h_img;
    }

    public void setH_img(String h_img) {
        this.h_img = h_img;
    }
}
