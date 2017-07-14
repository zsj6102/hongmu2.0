package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 地址实体类
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public class Address implements Serializable {
    /**
     * 地址ID
     */
    private int addrId;
    /**
     * 是否属于默认地址
     */
    private int def_addr;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 收货人联系方式
     */
    private String mobile;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 详细地址
     */
    private String address;

    private String zip;

    @Override
    public String toString() {
        return "Address{" +
                "addrId=" + addrId +
                ", def_addr=" + def_addr +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public int getDef_addr() {
        return def_addr;
    }

    public void setDef_addr(int def_addr) {
        this.def_addr = def_addr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
