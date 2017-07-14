package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 购车
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/10
 */
public class CartItem implements Serializable {
    /**
     * 购物车的ID
     */
    private int catid;
    /**
     * 商品ID
     */
    private  int product_id;
    /**
     * 商品缩略图地址
     */
    private String image_default;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格
     */
    private double price;
    /**
     * 商品特点
     */
    private String specs;
    /**
     * 是否选中标识
     */
    private boolean chooseState;
    /**
     * 暂定为购物车商品数量名
     */
    private int num;

    private int goods_id;

    @Override
    public String toString() {
        return "CartItem{" +
                "catid=" + catid +
                ", product_id=" + product_id +
                ", image_defaul='" + image_default + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", specs='" + specs + '\'' +
                ", chooseState=" + chooseState +
                ", count=" + num +
                '}';
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage_default() {
        return image_default;
    }

    public void setImage_default(String image_default) {
        this.image_default = image_default;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isChooseState() {
        return chooseState;
    }

    public void setChooseState(boolean chooseState) {
        this.chooseState = chooseState;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
}
