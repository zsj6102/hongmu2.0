package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 我的定制的Item 项
 * @Email 20000263@qq.com
 * @date 2016/8/11
 */
public class Custom implements Serializable {
    /**
     * 定制订单Id
     */
    private int cm_id;
    /**
     * 0：定制中 1：已完成
     */
    private int cm_status;
    /**
     * 0：待审核 1：立即付款2:待付款3:新的定制4:待付首款5:待付尾款
     */
    private int stateName;
    /**
     * 定制申请时间
     */
    private long time;
    /**
     * 官方定制id
     */
    private int oc_id;
    /**
     * 展示图片
     */
    private String img;
    /**
     * 商品名
     */
    private String name;
    /**
     * 价格
     */
    private double price;
    /**
     * 货品ID,用于下单
     */
    private int product_id;
    /**
     * 商品ID,用于下单
     */
    private int goods_id;
    /**
     * 下单类型,用于下单
     */
    private int itemtype;
    /**
     * 商品数量.用于下单
     */
    private int num;
    /**
     * 添加购物车类型,用于下单
     */
    private int addtype;
    /**
     * 官方定制尾款订单id
     */
    private int order_id_sub;

    /**
     *付私人定制 、首款 使用Id
     */
    private int  order_id;
    /**
     * 定制类型0:私人1:官方
     */
    private int type;


    @Override
    public String toString() {
        return "Custom{" +
                "cm_id=" + cm_id +
                ", cm_status=" + cm_status +
                ", stateName=" + stateName +
                ", time=" + time +
                ", oc_id=" + oc_id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", product_id=" + product_id +
                ", goods_id=" + goods_id +
                ", itemtype=" + itemtype +
                ", num=" + num +
                ", addtype=" + addtype +
                ", order_id_sub=" + order_id_sub +
                ", order_id=" + order_id +
                ", type=" + type +
                '}';
    }

    public int getCm_id() {
        return cm_id;
    }

    public void setCm_id(int cm_id) {
        this.cm_id = cm_id;
    }

    public int getCm_status() {
        return cm_status;
    }

    public void setCm_status(int cm_status) {
        this.cm_status = cm_status;
    }

    public int getStateName() {
        return stateName;
    }

    public void setStateName(int stateName) {
        this.stateName = stateName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getOc_id() {
        return oc_id;
    }

    public void setOc_id(int oc_id) {
        this.oc_id = oc_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAddtype() {
        return addtype;
    }

    public void setAddtype(int addtype) {
        this.addtype = addtype;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getOrder_id_sub() {
        return order_id_sub;
    }

    public void setOrder_id_sub(int order_id_sub) {
        this.order_id_sub = order_id_sub;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
