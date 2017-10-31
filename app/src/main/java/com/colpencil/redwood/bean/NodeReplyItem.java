package com.colpencil.redwood.bean;

import java.io.Serializable;

public class NodeReplyItem implements Serializable {
    private int re_member_id;//评论会员id
    private String re_store_name;//评论会员昵称或商家名称
    private String re_member_photo;//评论会员等级图标
    private String re_face;//评论会员头像
    private int re_store_type;//商家类型
    private int re_id;//评论id
    private String re_content;//评论内容
    private String re_create_time;//评论时间
    private int re_con_count;//该评论被评论的数量
    private int re_like_count;//该评论被点赞的数量
    private int isfocus;

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }

    public int getRe_member_id() {
        return re_member_id;
    }

    public void setRe_member_id(int re_member_id) {
        this.re_member_id = re_member_id;
    }

    public String getRe_store_name() {
        return re_store_name;
    }

    public void setRe_store_name(String re_store_name) {
        this.re_store_name = re_store_name;
    }

    public String getRe_member_photo() {
        return re_member_photo;
    }

    public void setRe_member_photo(String re_member_photo) {
        this.re_member_photo = re_member_photo;
    }

    public String getRe_face() {
        return re_face;
    }

    public void setRe_face(String re_face) {
        this.re_face = re_face;
    }

    public int getRe_store_type() {
        return re_store_type;
    }

    public void setRe_store_type(int re_store_type) {
        this.re_store_type = re_store_type;
    }

    public int getRe_id() {
        return re_id;
    }

    public void setRe_id(int re_id) {
        this.re_id = re_id;
    }

    public String getRe_content() {
        return re_content;
    }

    public void setRe_content(String re_content) {
        this.re_content = re_content;
    }

    public String getRe_create_time() {
        return re_create_time;
    }

    public void setRe_create_time(String re_create_time) {
        this.re_create_time = re_create_time;
    }

    public int getRe_con_count() {
        return re_con_count;
    }

    public void setRe_con_count(int re_con_count) {
        this.re_con_count = re_con_count;
    }

    public int getRe_like_count() {
        return re_like_count;
    }

    public void setRe_like_count(int re_like_count) {
        this.re_like_count = re_like_count;
    }
}
