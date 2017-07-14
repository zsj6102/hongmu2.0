package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:商品评论
 * @Email DramaScript@outlook.com
 * @date 2016/8/12
 */
public class GoodComment implements Serializable {

    private String content;
    private int comment_id;
    private String face;
    private String nickname;
    private long dateline;
    private String goodsname;
    private String specs;
    private List<String> imglist;
    private String member_photo;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
        this.dateline = dateline;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }
}
