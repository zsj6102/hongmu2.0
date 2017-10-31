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
    private List<String> imgsori_img;
    private String member_photo;
    private int isfocus;
    private int re_like_count;
    private int  discuss_total;

    public List<String> getImgsori_img() {
        return imgsori_img;
    }

    public void setImgsori_img(List<String> imgsori_img) {
        this.imgsori_img = imgsori_img;
    }

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }

    public int getRe_like_count() {
        return re_like_count;
    }

    public void setRe_like_count(int re_like_count) {
        this.re_like_count = re_like_count;
    }

    public int getDiscuss_total() {
        return discuss_total;
    }

    public void setDiscuss_total(int discuss_total) {
        this.discuss_total = discuss_total;
    }

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
