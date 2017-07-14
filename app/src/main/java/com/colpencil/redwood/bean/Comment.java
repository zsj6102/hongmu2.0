package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/9 14 11
 */
public class Comment implements Serializable {
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价id
     */
    private int comment_id;
    /**
     * 评论图片list
     */
    private List<String> imgList;
    /**
     * 成交价
     */
    private float payPrice;
    /**
     * 商品名
     */
    private String name;
    /**
     * 评论时间
     */
    private long dateline;
    /**
     * 商品id
     */
    private int good_id;
    /**
     * 商品规格
     */
    private String specs;

    private int com_type;

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", comment_id=" + comment_id +
                ", imgList=" + imgList +
                ", payPrice=" + payPrice +
                ", name='" + name + '\'' +
                ", dateline=" + dateline +
                ", good_id=" + good_id +
                ", specs='" + specs + '\'' +
                '}';
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

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(float payPrice) {
        this.payPrice = payPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateline() {
        return dateline;
    }

    public void setDateline(long dateline) {
        this.dateline = dateline;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public int getCom_type() {
        return com_type;
    }

    public void setCom_type(int com_type) {
        this.com_type = com_type;
    }
}
