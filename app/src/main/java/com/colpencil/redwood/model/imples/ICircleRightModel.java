package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.StatisticResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description:我的Model接口类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICircleRightModel extends ColpencilModel{

    void loadStatic(String member_id, String token);
    void sub(Observer<StatisticResult> observer);
}
