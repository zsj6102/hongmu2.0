package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class Goods_list implements Serializable {
    private List<Goods_list_item> goods_list;
    private List<BannerVo> adv_list;

    public List<Goods_list_item> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<Goods_list_item> goods_list) {
        this.goods_list = goods_list;
    }

    public List<BannerVo> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<BannerVo> adv_list) {
        this.adv_list = adv_list;
    }
}
