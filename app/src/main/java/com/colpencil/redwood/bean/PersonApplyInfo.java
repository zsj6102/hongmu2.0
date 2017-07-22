package com.colpencil.redwood.bean;

import java.io.File;
import java.io.Serializable;

public class PersonApplyInfo implements Serializable {
    private String store_type;
    private String real_name;
    private String id_number;
    private File id_img;
    private File id_img_opposite;
    private String region_id;
    private String address;
    private String biz_type;
    private String card;
    private String card_type;
    private String cardholder;

    public String getStore_type() {
        return store_type;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public File getId_img() {
        return id_img;
    }

    public void setId_img(File id_img) {
        this.id_img = id_img;
    }

    public File getId_img_opposite() {
        return id_img_opposite;
    }

    public void setId_img_opposite(File id_img_opposite) {
        this.id_img_opposite = id_img_opposite;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(String biz_type) {
        this.biz_type = biz_type;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }
}
