package com.colpencil.redwood.bean;

import java.io.Serializable;

public class HomeGoodInfo implements Serializable {

    private String goodsName;
    private int goodsid;
    private float costprice;
    private String image;
    private String goodsname;
    private float costPrice;
    private int goodsId;

    @Override
    public String toString() {
        return "HomeGoodInfo{" +
                "goodsname='" + goodsName + '\'' +
                ", goodsid=" + goodsid +
                ", costprice=" + costprice +
                ", image='" + image + '\'' +
                '}';
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public float getCostprice() {
        return costprice;
    }

    public void setCostprice(float costprice) {
        this.costprice = costprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
