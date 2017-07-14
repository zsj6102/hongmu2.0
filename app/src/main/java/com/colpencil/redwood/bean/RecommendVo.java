package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈 宝
 * @Description:为你推荐
 * @Email 1041121352@qq.com
 * @date 2016/9/22
 */
public class RecommendVo implements Serializable {

    /**
     * 模块
     */
    private int rectype;

    /**
     * 商品列表
     */
    private List<HomeGoodInfo> goodsList;

    /**
     * banner列表
     */
    private List<BannerVo> advList;

    public int getRectype() {
        return rectype;
    }

    public void setRectype(int rectype) {
        this.rectype = rectype;
    }

    public List<HomeGoodInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HomeGoodInfo> goodsList) {
        this.goodsList = goodsList;
    }

    public List<BannerVo> getAdvList() {
        return advList;
    }

    public void setAdvList(List<BannerVo> advList) {
        this.advList = advList;
    }
}
