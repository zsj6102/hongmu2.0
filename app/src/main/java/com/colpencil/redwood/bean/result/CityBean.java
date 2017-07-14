package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.CountryBean;

import java.io.Serializable;
import java.util.List;

public class CityBean implements Serializable{
    private int city_id;
    private String city_name;
    private List<CountryBean> list_country;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public List<CountryBean> getList_country() {
        return list_country;
    }

    public void setList_country(List<CountryBean> list_country) {
        this.list_country = list_country;
    }
}
