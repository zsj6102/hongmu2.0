package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.result.OfficialResult.ResultBean;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:官方定制列表
 * @Email DramaScript@outlook.com
 * @date 2016/9/1
 */
public interface ICustomListView extends ColpencilBaseView {

    /**
     * 加载商品
     * @param result
     */
    void loadGood(List<ResultBean> result);

    /**
     * 加载错误
     * @param msg
     */
    void loadError(String msg);

}
