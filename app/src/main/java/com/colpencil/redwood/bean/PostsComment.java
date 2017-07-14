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
