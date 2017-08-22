package com.colpencil.redwood.bean;

import java.io.Serializable;

public class ItemStoreFans implements Serializable {
    private int member_id;  //会员id
    private String store_name; //商家名称或会员名称
    private String member_photo;//会员等级图标
    private int store_count;//粉丝个数
    private String face;//会员头像
    private String store_id;//商家id
    private int store_type;//商家类型
    private String praise_rate;//好评率
    private String store_type_path;//商家类型图标
    private int prize_count;//获奖作品个数
    private int store_recommend;//是否推荐
    private int isfocus;//是否已关注

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }

    public String getPraise_rate() {
        return praise_rate;
    }

    public void setPraise_rate(String praise_rate) {
        this.praise_rate = praise_rate;
    }

    public String getStore_type_path() {
        return store_type_path;
    }

    public void setStore_type_path(String store_type_path) {
        this.store_type_path = store_type_path;
    }

    public int getPrize_count() {
        return prize_count;
    }

    public void setPrize_count(int prize_count) {
        this.prize_count = prize_count;
    }

    public int getStore_recommend() {
        return store_recommend;
    }

    public void setStore_recommend(int store_recommend) {
        this.store_recommend = store_recommend;
    }

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }
}
