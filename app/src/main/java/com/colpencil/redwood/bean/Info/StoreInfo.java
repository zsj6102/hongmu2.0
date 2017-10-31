package com.colpencil.redwood.bean.Info;

import java.io.Serializable;

/**
 * 店铺信息
 */
public class StoreInfo implements Serializable {
    private Integer store_id   ;   // 商家id
    private String store_name  ;   // 商店名称
    private String attr        ;   // 地址
    private String biz_type    ;   // 类型
    private String return_attr ;   // 退货地址
    private String description ;   // 描述
    private String sign        ;   // 签名
    private String store_banner;   // 通知
    private Integer has_margin ;   // 是否交了保证金 0未交 1 已交
    private String store_logo  ;   // 商店logo
    private Integer has_settle ;   // 是否交了入住费  0未交 1 已交
    private String craft       ;   // 手艺特色   备注：名师名匠店铺设置特有的
    private String goods_cat_id;//经营品类的id
    private Integer store_regionid;//商家店铺地址区id
    private Integer return_region;//退货地址的区id
    private String return_address; //退货省市区
    private String return_address_detail;//退货详细地址
    private String store_address;//店铺省市区
    private String store_address_detail;//店铺详细地址
    private String return_man;//收货人
    private String return_mobile;//收货人电话
    private String settleInfo;

    public String getSettleInfo() {
        return settleInfo;
    }

    public void setSettleInfo(String settleInfo) {
        this.settleInfo = settleInfo;
    }

    public String getReturn_man() {
        return return_man;
    }

    public void setReturn_man(String return_man) {
        this.return_man = return_man;
    }

    public String getReturn_mobile() {
        return return_mobile;
    }

    public void setReturn_mobile(String return_mobile) {
        this.return_mobile = return_mobile;
    }

    public String getReturn_address() {
        return return_address;
    }

    public void setReturn_address(String return_address) {
        this.return_address = return_address;
    }

    public String getReturn_address_detail() {
        return return_address_detail;
    }

    public void setReturn_address_detail(String return_address_detail) {
        this.return_address_detail = return_address_detail;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_address_detail() {
        return store_address_detail;
    }

    public void setStore_address_detail(String store_address_detail) {
        this.store_address_detail = store_address_detail;
    }

    public String getGoods_cat_id() {
        return goods_cat_id;
    }

    public void setGoods_cat_id(String goods_cat_id) {
        this.goods_cat_id = goods_cat_id;
    }


    public Integer getStore_regionid() {
        return store_regionid;
    }

    public void setStore_regionid(Integer store_regionid) {
        this.store_regionid = store_regionid;
    }

    public Integer getReturn_region() {
        return return_region;
    }

    public void setReturn_region(Integer return_region) {
        this.return_region = return_region;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getBiz_type() {
        return biz_type;
    }

    public void setBiz_type(String biz_type) {
        this.biz_type = biz_type;
    }

    public String getReturn_attr() {
        return return_attr;
    }

    public void setReturn_attr(String return_attr) {
        this.return_attr = return_attr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStore_banner() {
        return store_banner;
    }

    public void setStore_banner(String store_banner) {
        this.store_banner = store_banner;
    }

    public Integer getHas_margin() {
        return has_margin;
    }

    public void setHas_margin(Integer has_margin) {
        this.has_margin = has_margin;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public Integer getHas_settle() {
        return has_settle;
    }

    public void setHas_settle(Integer has_settle) {
        this.has_settle = has_settle;
    }

    public String getCraft() {
        return craft;
    }

    public void setCraft(String craft) {
        this.craft = craft;
    }
}
