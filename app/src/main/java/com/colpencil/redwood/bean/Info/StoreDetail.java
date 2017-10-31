package com.colpencil.redwood.bean.Info;

import com.colpencil.redwood.bean.AllAuctionItem;

import java.io.Serializable;
import java.util.List;

public class StoreDetail implements Serializable {
    private Integer member_id;//会员id
    private Integer store_id;//商家id
    private int store_type;//商家类型
    private String store_name;//商家名称
    private String store_path;//商家头像
    private String praise_rate;//商家好评率
    private String store_city;//商家城市地
    private String sign;//签名
    private String store_banner;//通知
    private String description;//商家介绍
    private String referrer;// 推荐人名称
    private String lv_name;// 会员等级名
    private String member_photo;//会员等级图片
    private String store_type_name;//商家类型名称
    private String store_type_path;//商家类型图片
    private Integer isfocus;//是否关注
    private List<AllAuctionItem> biz_type;//主创类别
    private String catname;
    private String face;
    private int store_count;
    private int prize_count;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getStore_count() {
        return store_count;
    }

    public void setStore_count(int store_count) {
        this.store_count = store_count;
    }

    public int getPrize_count() {
        return prize_count;
    }

    public void setPrize_count(int prize_count) {
        this.prize_count = prize_count;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
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

    public String getStore_city() {
        return store_city;
    }

    public void setStore_city(String store_city) {
        this.store_city = store_city;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStore_banner() {
        return store_banner;
    }

    public void setStore_banner(String store_banner) {
        this.store_banner = store_banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
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

    public Integer getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(Integer isfocus) {
        this.isfocus = isfocus;
    }

    public List<AllAuctionItem> getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(List<AllAuctionItem> biz_type) {
        this.biz_type = biz_type;
    }
}
