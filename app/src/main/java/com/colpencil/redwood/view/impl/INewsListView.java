package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.NewsInfoVo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:新闻动态的View接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface INewsListView extends ColpencilBaseView {

    /**
     * 获取新闻列表
     *
     * @param list
     */
    void refresh(List<NewsInfoVo> list);

    void loadMore(List<NewsInfoVo> list);

    void loadError(String msg);

    void getStatusSucess(ApplyStatusReturn applyStatusReturn);
    void getStatusError(String message);
}
