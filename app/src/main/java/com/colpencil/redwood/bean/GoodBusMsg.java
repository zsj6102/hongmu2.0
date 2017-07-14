package com.colpencil.redwood.bean;

public class GoodBusMsg {

    private int productId;
    private int goodsNum;
    private String type;
    private int goodsId;
    private String norm;
    private String specification;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }
}
