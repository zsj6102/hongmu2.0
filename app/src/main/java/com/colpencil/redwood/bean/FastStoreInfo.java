package com.colpencil.redwood.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class FastStoreInfo implements Serializable {
    private String cat_id;
    private int type_id;
    private String name;
    private String price;
    private String store;
    private String intro;
    private int store_id;
    private String goods_type;
    private File cover;
    private List<File> images;
    private String warehouseOrshelves;
    private double mktprice;
    private String specs;
    private int spe_section_id;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public File getCover() {
        return cover;
    }

    public void setCover(File cover) {
        this.cover = cover;
    }

    public List<File> getImages() {
        return images;
    }

    public void setImages(List<File> images) {
        this.images = images;
    }

    public String getWarehouseOrshelves() {
        return warehouseOrshelves;
    }

    public void setWarehouseOrshelves(String warehouseOrshelves) {
        this.warehouseOrshelves = warehouseOrshelves;
    }

    public double getMktprice() {
        return mktprice;
    }

    public void setMktprice(double mktprice) {
        this.mktprice = mktprice;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public int getSpe_section_id() {
        return spe_section_id;
    }

    public void setSpe_section_id(int spe_section_id) {
        this.spe_section_id = spe_section_id;
    }
}
