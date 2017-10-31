package com.colpencil.redwood.bean;

import java.io.Serializable;

public class Settled implements Serializable {
    private Double cost;
    private Integer id;
    private Integer sort;
    private Integer year;
    private String year_string;
    private boolean choose;

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getYear_string() {
        return year_string;
    }

    public void setYear_string(String year_string) {
        this.year_string = year_string;
    }
}
