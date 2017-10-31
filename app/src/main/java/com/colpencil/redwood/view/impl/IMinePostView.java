package com.colpencil.redwood.view.impl;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilBaseView;

import java.util.List;

public interface IMinePostView extends ColpencilBaseView {
    void refresh(ResultInfo<List<MinePostItem>> data);

    void loadMore(ResultInfo<List<MinePostItem>> data);

    void loadFail(String msg);

    void operate(EntityResult<String> result, int type);
}
