package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CardInfo implements Serializable{
    private int member_id;
    private String store_name;
    private String member_photo;
    private int store_count;
    private String face;
    private String store_id;
    private int store_type;
    private String praise_rate;
    private String store_type_path;
    private int prize_count;
    private int isfocus;
    private int store_recommend;

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
