package com.colpencil.redwood.bean;
import com.colpencil.redwood.bean.Info.StoreDetail;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:商品的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodInfo implements Serializable {

    private String goodsname;
    private double saleprice;
    private int goodsales;
    private int goodsid;
    private double costprice;
    private List<String> goodservice;
    private List<String> imglist;
    private List<PromotionVo> promotions;
    private List<Goodspec> goodspec;
    private List<Product> productList;
    private StoreDetail store_info;

    public StoreDetail getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreDetail store_info) {
        this.store_info = store_info;
    }

    @Override
    public String toString() {
        return "GoodInfo{" +
                "goodsname='" + goodsname + '\'' +
                ", saleprice=" + saleprice +
                ", goodsales=" + goodsales +
                ", goodsid=" + goodsid +
                ", costprice=" + costprice +
                ", goodservice=" + goodservice +
                ", imglist=" + imglist +
                ", promotions=" + promotions +
                ", goodspec=" + goodspec +
                ", productList=" + productList +
                '}';
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public int getGoodsales() {
        return goodsales;
    }

    public void setGoodsales(int goodsales) {
        this.goodsales = goodsales;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public double getCostprice() {
        return costprice;
    }

    public void setCostprice(double costprice) {
        this.costprice = costprice;
    }

    public List<String> getGoodservice() {
        return goodservice;
    }

    public void setGoodservice(List<String> goodservice) {
        this.goodservice = goodservice;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }

    public List<PromotionVo> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionVo> promotions) {
        this.promotions = promotions;
    }

    public List<Goodspec> getGoodspec() {
        return goodspec;
    }

    public void setGoodspec(List<Goodspec> goodspec) {
        this.goodspec = goodspec;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
