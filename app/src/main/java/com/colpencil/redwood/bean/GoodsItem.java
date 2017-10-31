package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 商品收藏子项
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class GoodsItem implements Serializable {
    /**
     * 商品价格
     */
    private double price;
    /**
     * 销量
     */
    private int buy_count;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品id
     */
    private int goods_id;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 收藏id
     */
    private int favorite_id;
    /**
     *  浏览记录id
     */
    private int foot_id;

    @Override
    public String toString() {
        return "GoodsItem{" +
                "price=" + price +
                ", buy_count=" + buy_count +
                ", name='" + name + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", image='" + image + '\'' +
                ", favorite_id=" + favorite_id +
                ", foot_id=" + foot_id +
                '}';
    }

    public int getFoot_id() {
        return foot_id;
    }

    public void setFoot_id(int foot_id) {
        this.foot_id = foot_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBuy_count() {
        return buy_count;
    }

    public void setBuy_count(int buy_count) {
        this.buy_count = buy_count;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }
}
