package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈 宝
 * @Description:首页中间的实体类
 * @Email 1041121352@qq.com
 * @date 2016/10/20
 */
public class RemoduleVo implements Serializable {

    /**
     * 为你推荐模块
     */
    private List<RecommendVo> recModuleList;

    private List<BannerVo> recHead;

    public List<RecommendVo> getRecModuleList() {
        return recModuleList;
    }

    public void setRecModuleList(List<RecommendVo> recModuleList) {
        this.recModuleList = recModuleList;
    }

    public List<BannerVo> getRecHead() {
        return recHead;
    }

    public void setRecHead(List<BannerVo> recHead) {
        this.recHead = recHead;
    }
}
