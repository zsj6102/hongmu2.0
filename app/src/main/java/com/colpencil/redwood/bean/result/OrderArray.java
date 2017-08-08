package com.colpencil.redwood.bean.result;


import com.colpencil.redwood.bean.Postages;

import java.io.Serializable;
import java.util.List;

/**
 * 订单集合
 */
public class OrderArray implements Serializable {
    /**
     * 商家id
     */
    private int storeId;
    /**
     * 商家名称
     */
    private String storeName;
    /**
     * 商品item对象集合
     */
    private List<OrderGoodsItem> goodsItem;
    /**
     * 含原始价格，满减后的价格，满减价格
     */
    private OrderPrice orderPrice;
    /**
     * 快递相关对象
     */
    private  List<Postages> postages;
    /**
     * 商家购物车id字符数组
     */
    private String store_cart_ids;
    /**
     * 卡券对象
     */
    private AllCoupon allCoupon;

    /**
     * 商家留言,用户评价后写进来
     */
    private String spec;
    /**
     * 优惠券
     */
    private double youhui;
    private int youhuiid = -1;
    /**
     * 代金券
     */
    private double daijin;

    private String daijinids;
    /**
     * 快递方式
     * @return
     */
    private String deliver;

    private int deliverid = -1;


    /**
     * 快递价格
     */
    private double deliverPrice;

    /**
     * 需要付的
     * @return
     */
    private double needPay;
    public int getYouhuiid() {
        return youhuiid;
    }

    public void setYouhuiid(int youhuiid) {
        this.youhuiid = youhuiid;
    }

    public String getDaijinid() {
        return daijinids;
    }

    public void setDaijinid(String daijinid) {
        this.daijinids = daijinid;
    }

    public int getDeliverid() {
        return deliverid;
    }

    public void setDeliverid(int deliverid) {
        this.deliverid = deliverid;
    }



    public double getYouhui() {
        return youhui;
    }

    public void setYouhui(double youhui) {
        this.youhui = youhui;
    }

    public double getDaijin() {
        return daijin;
    }

    public void setDaijin(double daijin) {
        this.daijin = daijin;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public double getDeliverPrice() {
        return deliverPrice;
    }

    public void setDeliverPrice(double deliverPrice) {
        this.deliverPrice = deliverPrice;
    }

    public double getNeedPay() {
        return needPay;
    }

    public void setNeedPay(double needPay) {
        this.needPay = needPay;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<OrderGoodsItem> getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(List<OrderGoodsItem> goodsItem) {
        this.goodsItem = goodsItem;
    }

    public OrderPrice getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(OrderPrice orderPrice) {
        this.orderPrice = orderPrice;
    }

    public  List<Postages> getPostages() {
        return postages;
    }

    public void setPostages( List<Postages> postages) {
        this.postages = postages;
    }

    public String getStore_cart_ids() {
        return store_cart_ids;
    }

    public void setStore_cart_ids(String store_cart_ids) {
        this.store_cart_ids = store_cart_ids;
    }

    public AllCoupon getAllCoupon() {
        return allCoupon;
    }

    public void setAllCoupon(AllCoupon allCoupon) {
        this.allCoupon = allCoupon;
    }

    public Object getItem(int position) {
        if (position == 0) {
            return storeName;
        } else {
            return goodsItem.get(position - 1);
        }
    }

    public int getItemCount(){
        return goodsItem.size()+1;
    }

}
