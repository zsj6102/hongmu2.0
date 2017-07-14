package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/12 09 54
 */
public class PayForData implements Serializable {
    /**
     * 地址
     */
    private Address address;
    /**
     * 邮寄方式
     */
    private List<Postages> postages;
    /**
     * 支付方式
     */
    private List<PayType> pays;
    /**
     * 各种价格
     */
    private KindOfPrice orderPrice;
    /**
     * 商品列表
     */
    private List<GoodOfOrder> goodsItem;
    /**
     *购物车id
     */
    private String cart_ids;

    @Override
    public String toString() {
        return "PayForData{" +
                "address=" + address +
                ", postages=" + postages +
                ", pays=" + pays +
                ", orderPrice=" + orderPrice +
                ", goodsItem=" + goodsItem +
                ", cart_ids='" + cart_ids + '\'' +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<GoodOfOrder> getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(List<GoodOfOrder> goodsItem) {
        this.goodsItem = goodsItem;
    }

    public KindOfPrice getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(KindOfPrice orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<PayType> getPays() {
        return pays;
    }

    public void setPays(List<PayType> pays) {
        this.pays = pays;
    }

    public List<Postages> getPostages() {
        return postages;
    }

    public void setPostages(List<Postages> postages) {
        this.postages = postages;
    }

    public String getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(String cart_ids) {
        this.cart_ids = cart_ids;
    }
}
