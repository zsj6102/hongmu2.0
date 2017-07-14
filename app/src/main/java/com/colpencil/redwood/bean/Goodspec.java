package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class Goodspec implements Serializable {

    private int spec_id;
    private String spec_name;
    private List<GoodNorm> valueList;

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public List<GoodNorm> getValueList() {
        return valueList;
    }

    public void setValueList(List<GoodNorm> valueList) {
        this.valueList = valueList;
    }
}
