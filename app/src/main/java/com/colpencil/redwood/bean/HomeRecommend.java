package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class HomeRecommend implements Serializable {

    /**
     * 顶部Banner
     */
    private List<BannerVo> topAdv;

    /**
     * 功能点
     */
    private List<FuncPointVo> funcList;

    /**
     * 为你推荐图片
     */
    private List<BannerVo> recHead;

    /**
     * 为你推荐模块
     */
    private List<RemoduleVo> recModuleSet;

    /**
     * 热门推荐
     */
    private List<BannerVo> hotHead;

    private List<BannerVo> middleAdv;


    public List<BannerVo> getTopAdv() {
        return topAdv;
    }

    public void setTopAdv(List<BannerVo> topAdv) {
        this.topAdv = topAdv;
    }

    public List<FuncPointVo> getFuncList() {
        return funcList;
    }

    public void setFuncList(List<FuncPointVo> funcList) {
        this.funcList = funcList;
    }

    public List<BannerVo> getRecHead() {
        return recHead;
    }

    public void setRecHead(List<BannerVo> recHead) {
        this.recHead = recHead;
    }

    public List<BannerVo> getHotHead() {
        return hotHead;
    }

    public void setHotHead(List<BannerVo> hotHead) {
        this.hotHead = hotHead;
    }

    public List<RemoduleVo> getRecModuleSet() {
            return recModuleSet;
    }

    public void setRecModuleSet(List<RemoduleVo> recModuleSet) {
        this.recModuleSet = recModuleSet;
    }

    public List<BannerVo> getMiddleAdv() {
        return middleAdv;
    }

    public void setMiddleAdv(List<BannerVo> middleAdv) {
        this.middleAdv = middleAdv;
    }
}
