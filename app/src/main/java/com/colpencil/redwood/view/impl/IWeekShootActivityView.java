package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:WeekShootActivity的view接口
 * @Email DramaScript@outlook.com
 * @date 2016/8/2
 */
public interface IWeekShootActivityView extends ColpencilBaseView {
    /**
     * @Description:获取周拍分类
     * @author 陈宝
     * @Email DramaScript@outlook.com
     * @date 2016/8/5
     */
    void loadTagSuccess(List<CategoryVo> tags);

    void loadTagError();

}
