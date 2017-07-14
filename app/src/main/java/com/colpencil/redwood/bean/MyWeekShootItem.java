package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description:周拍Item 项
 * @Email 20000263@qq.com
 * @date 2016/8/11
 */
public class MyWeekShootItem implements Serializable {
    /**
     * 竞拍ID
     */
    private int id;
    /**
     * 状态(0:未开始 1:进行中 2:结束)
     */
    private int status;
    /**
     * 商品名称
     */
    private String goods_name;
    /**
     * 竞拍时间
     */
    private long time;
    /**
     * false:未支付 true:已支付
     */
    private int buy_status;
    /**
     * 图片
     */
    private String img;

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

    private double price;
    /**
     * 0失败 1成功
     */
    private int result;

    /**
     * 当前价格
     */
    private String spot_price;

    private int statusName;

    private int stataName;

    private int logs_id;

    private int order_id;

    @Override
    public String toString() {
        return "MyWeekShootItem{" +
                "id=" + id +
                ", status=" + status +
                ", goods_name='" + goods_name + '\'' +
                ", time=" + time +
                ", buy_status=" + buy_status +
                ", img='" + img + '\'' +
                ", product_id=" + product_id +
                ", goods_id=" + goods_id +
                ", itemtype=" + itemtype +
                ", num=" + num +
                ", addtype=" + addtype +
                ", price=" + price +
                ", result=" + result +
                ", spot_price='" + spot_price + '\'' +
                '}';
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBuy_status() {
        return buy_status;
    }

    public void setBuy_status(int buy_status) {
        this.buy_status = buy_status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSpot_price() {
        return spot_price;
    }

    public void setSpot_price(String spot_price) {
        this.spot_price = spot_price;
    }

    public int getStatusName() {
        return statusName;
    }

    public void setStatusName(int statusName) {
        this.statusName = statusName;
    }

    public int getStataName() {
        return stataName;
    }

    public void setStataName(int stataName) {
        this.stataName = stataName;
    }

    public int getLogs_id() {
        return logs_id;
    }

    public void setLogs_id(int logs_id) {
        this.logs_id = logs_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
