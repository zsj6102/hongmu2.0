package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CategoryVo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

/**
 * @author 陈 宝
 * @Description:推荐百科的View接口
 * @Email 1041121352@qq.com
 * @date 2016/9/22
 */
public interface ITagView extends ColpencilBaseView {

    void loadTag(List<CategoryVo> list);

    void loadError(String msg, int code);

}
