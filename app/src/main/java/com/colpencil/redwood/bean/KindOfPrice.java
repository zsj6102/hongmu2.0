package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 各种价格
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/12
 */
public class KindOfPrice implements Serializable {
    /**
     * 原价
     */
    private double originalPrice;
    /**
     * 经过满减后的价格
     */
    private float goodsPrice;
    /**
     * 满减金额
     */
    private double discountPrice;

    @Override
    public String toString() {
        return "KindOfPrice{" +
                "originalPirce=" + originalPrice +
                ", goodsPrice=" + goodsPrice +
                ", discountPrice=" + discountPrice +
                '}';
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
