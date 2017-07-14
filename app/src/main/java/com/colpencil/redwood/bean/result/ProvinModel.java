package com.colpencil.redwood.bean.result;

import java.io.Serializable;
import java.util.List;

public class ProvinModel implements Serializable{
    private String province_name;
    private int province_id;
    List<CityBean>  province_next_city;

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public List<CityBean> getProvince_next_city() {
        return province_next_city;
    }

    public void setProvince_next_city(List<CityBean> province_next_city) {
        this.province_next_city = province_next_city;
    }
}
