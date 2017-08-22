package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class JiashangItem implements Serializable {
    /**
     * 个人商家主页-架上拍品
     商家主页-藏品货架-速拍货架
     名师名匠主页-藏品货架-速拍货架
     */
    private int collectmember;//收藏个数
    private String name;//商品名称
    private int goods_id;//商品id
    private String intro;//商品介绍
    private double price;//商品价格
    private String create_time;//发布时间
    private String store;//库存
    private int comment_count;//评论数
    private String cover;//商品广告图
    private List<UrlString> list_view_gallery;//商品图片

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

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<UrlString> getList_view_gallery() {
        return list_view_gallery;
    }

    public void setList_view_gallery(List<UrlString> list_view_gallery) {
        this.list_view_gallery = list_view_gallery;
    }
}
