package com.colpencil.redwood.bean.Info;

import com.colpencil.redwood.bean.AllGoodsImgInfo;

import java.io.Serializable;
import java.util.List;

public class ApplyGoodInfo implements Serializable {
    private Integer goods_id;  // 商品类型
    private Integer cat_id;  // 商品类型的父类
    private String name;  // 名称
    private Double price;  // 价格
    private Integer store;  // 库存
    private String intro;  // 详细介绍
    private Integer store_id;  // 商家id
    private String goods_type;  // 那个区域发布的商品
    private String cover;  // 封面
    private Double mktprice;  // 用于品牌的字段  市场价
    private String spe_section_name;//专场名称
    private Integer spe_section_id;  // 用于专场的子段  专场id
    private List<AllGoodsImgInfo> imagelist;  // 商品图片
    private float cover_proportion;//宽高比获取
    private String cat_name;

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Double getMktprice() {
        return mktprice;
    }

    public void setMktprice(Double mktprice) {
        this.mktprice = mktprice;
    }

    public String getSpe_section_name() {
        return spe_section_name;
    }

    public void setSpe_section_name(String spe_section_name) {
        this.spe_section_name = spe_section_name;
    }

    public Integer getSpe_section_id() {
        return spe_section_id;
    }

    public void setSpe_section_id(Integer spe_section_id) {
        this.spe_section_id = spe_section_id;
    }

    public List<AllGoodsImgInfo> getImagelist() {
        return imagelist;
    }

    public void setImagelist(List<AllGoodsImgInfo> imagelist) {
        this.imagelist = imagelist;
    }

    public float getCover_proportion() {
        return cover_proportion;
    }

    public void setCover_proportion(float cover_proportion) {
        this.cover_proportion = cover_proportion;
    }
}
