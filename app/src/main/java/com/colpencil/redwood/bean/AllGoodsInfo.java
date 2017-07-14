package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class AllGoodsInfo implements Serializable {
    private int store_id;
    private String store_type;
    private String store_logo;
    private String store_name;
    private String store_city;
    private String goods_rate;
    private String point;
    private int collectmember;
    private String name;
    private int goods_id;
    private String intro;
    private double price;
    private String create_time;

    private int store;
    private int comment_count;
    private List<AllGoodsImgInfo> list_view_gallery;
    private int is_top;
    private String cover;
    private int fans_count;
    private String point_name;
    private String store_type_name;


    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_type() {
        return store_type;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_city() {
        return store_city;
    }

    public void setStore_city(String store_city) {
        this.store_city = store_city;
    }

    public String getGoods_rate() {
        return goods_rate;
    }

    public void setGoods_rate(String goods_rate) {
        this.goods_rate = goods_rate;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getCollectmember() {
        return collectmember;
    }

    public void setCollectmember(int collectmember) {
        this.collectmember = collectmember;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public List<AllGoodsImgInfo> getList_view_gallery() {
        return list_view_gallery;
    }

    public void setList_view_gallery(List<AllGoodsImgInfo> list_view_gallery) {
        this.list_view_gallery = list_view_gallery;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getStore_type_name() {
        return store_type_name;
    }

    public void setStore_type_name(String store_type_name) {
        this.store_type_name = store_type_name;
    }
}
