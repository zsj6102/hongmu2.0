package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class RatedItem implements Serializable {
    private int comment_id;//评论id
    private String content;//内容
    private String dateline;//评论时间
    private String name;//商品名称
    private String thumbnail;//商品图片
    private double price;//商品价格
    private int like_num;//点赞的个数
    private int discuss_num;//评论的数量
    private String face;//评论人的头像
    private String nickname;// 评论人名称
    private List<String> img;//评论人发的图片
    private String store_name;//商家名称

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeteline() {
        return dateline;
    }

    public void setDeteline(String deteline) {
        this.dateline = deteline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getDiscuss_num() {
        return discuss_num;
    }

    public void setDiscuss_num(int discuss_num) {
        this.discuss_num = discuss_num;
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

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
