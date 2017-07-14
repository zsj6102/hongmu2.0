package com.colpencil.redwood.bean.result;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:官方推荐定制结果集
 * @Email DramaScript@outlook.com
 * @date 2016/8/11
 */
public class CustomResult implements Serializable {
    /**
     * 货品ID,用于生产订单的
     */
    private int product_id;
    /**
     * 商品ID,用于生产订单
     */
    private int goods_id;
    /**
     * 用于订单 :5
     */
    private int itemtype;
    /**
     * 商品数量.用于订单 :1
     */
    private int num;
    /**
     * 添加购物车类型 :1
     */
    private int addtype;
    private String code;
    private String msg;
    /**
     * H5地址
     */
    private String url;
    /**
     * 价格
     */
    private double goods_price;

    @Override
    public String toString() {
        return "CustomResult{" +
                "product_id=" + product_id +
                ", goods_id=" + goods_id +
                ", itemtype=" + itemtype +
                ", num=" + num +
                ", addtype=" + addtype +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", url='" + url + '\'' +
                ", goods_price=" + goods_price +
                '}';
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public int getAddtype() {
        return addtype;
    }

    public void setAddtype(int addtype) {
        this.addtype = addtype;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }
}
