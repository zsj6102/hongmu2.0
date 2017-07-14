package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.AnnounceResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description:公告的model接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface IHelpModel extends ColpencilModel{

    void loadAboutus();
    void sub(Observer<AnnounceResult> observer);

    void loadHelp();
    void subhelp(Observer<AnnounceResult> observer);
}
