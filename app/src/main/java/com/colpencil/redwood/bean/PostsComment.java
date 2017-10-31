package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:商品评论的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class PostsComment implements Serializable {

    private String re_content;
    private String nickname;
    private int comment_id;
    private String face;
    private long time;
    private long createtime;
    private String member_photo;
    private int isfocus;
    private int re_con_count;
    private int re_like_count;
    private int re_id;

    public int getRe_id() {
        return re_id;
    }

    public void setRe_id(int re_id) {
        this.re_id = re_id;
    }

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
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

    public String getRe_content() {
        return re_content;
    }

    public void setRe_content(String re_content) {
        this.re_content = re_content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }
}
