package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.CatListBean;
import com.colpencil.redwood.bean.CoverSpecialItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.SizeColorInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface PublishView extends ColpencilBaseView{
    void applyError(String message);

    void loadCat(CatListBean catListBean);

    void loadSize(SizeColorInfo info);

    void loadZcList(ResultInfo<List<CoverSpecialItem>> resultInfo);

    void loadPro(ResultInfo<String> result);
}
