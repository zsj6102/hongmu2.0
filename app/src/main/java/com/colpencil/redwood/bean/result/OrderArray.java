package com.colpencil.redwood.bean.result;


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
