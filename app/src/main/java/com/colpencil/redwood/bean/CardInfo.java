package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CardInfo implements Serializable{
    private int member_id;//会员id
    private String store_name;//商家名称
    private String member_photo;//会员等级图标
    private int store_count;//粉丝个数
    private String face;//会员头像
    private Integer store_id;//商家id
    private Integer store_type;//商家类型
    private String praise_rate;//好评率
    private String store_type_path;//商家类型图标
    private Integer prize_count;//获奖作品个数
    private int isfocus;//是否已关注
    private int store_recommend;//是否推荐
    private String lv_name;//等级名称


    public String getLv_name() {
        return lv_name;
    }

    public void setLv_name(String lv_name) {
        this.lv_name = lv_name;
    }

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

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public Integer getStore_type() {
        return store_type;
    }

    public void setStore_type(Integer store_type) {
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

    public Integer getPrize_count() {
        return prize_count;
    }

    public void setPrize_count(Integer prize_count) {
        this.prize_count = prize_count;
    }

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }

    public int getStore_recommend() {
        return store_recommend;
    }

    public void setStore_recommend(int store_recommend) {
        this.store_recommend = store_recommend;
    }
}
