package com.colpencil.redwood.bean.result;

import java.io.Serializable;
import java.util.List;

public class GoodReply implements Serializable {
    private DetailMap detail_map;
    private List<DiscussMap> list_map;

    public DetailMap getDetail_map() {
        return detail_map;
    }

    public void setDetail_map(DetailMap detail_map) {
        this.detail_map = detail_map;
    }

    public List<DiscussMap> getList_map() {
        return list_map;
    }

    public void setList_map(List<DiscussMap> list_map) {
        this.list_map = list_map;
    }
}
