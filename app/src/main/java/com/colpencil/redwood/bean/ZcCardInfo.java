package com.colpencil.redwood.bean;

import java.io.Serializable;

public class ZcCardInfo implements Serializable {
    private int member_id;
    private Integer store_id;
    private Integer store_type;
    private String store_type_name;
    private String store_type_path;
    private String store_name;
    private String store_path;
    private String praise_rate;
    private String face;

    private int store_count;
    private int isfocus;
    private int prize_count;
    private String lv_name;
    private String member_photo;
    private int store_recommend;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getStore_recommend() {
        return store_recommend;
    }

    public void setStore_recommend(int store_recommend) {
        this.store_recommend = store_recommend;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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

    public String getStore_type_name() {
        return store_type_name;
    }

    public void setStore_type_name(String store_type_name) {
        this.store_type_name = store_type_name;
    }

    public String getStore_type_path() {
        return store_type_path;
    }

    public void setStore_type_path(String store_type_path) {
        this.store_type_path = store_type_path;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_path() {
        return store_path;
    }

    public void setStore_path(String store_path) {
        this.store_path = store_path;
    }

    public String getPraise_rate() {
        return praise_rate;
    }

    public void setPraise_rate(String praise_rate) {
        this.praise_rate = praise_rate;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }

    public int getPrize_count() {
        return prize_count;
    }

    public void setPrize_count(int prize_count) {
        this.prize_count = prize_count;
    }

    public String getLv_name() {
        return lv_name;
    }

    public void setLv_name(String lv_name) {
        this.lv_name = lv_name;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }
}
