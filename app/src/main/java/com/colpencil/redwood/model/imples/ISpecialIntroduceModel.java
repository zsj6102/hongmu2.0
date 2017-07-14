package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

public interface ISpecialIntroduceModel extends ColpencilModel {

    /**
     * 获取专场介绍
     */
    void getSpecialIntroduce(int id);

    void subGetSpecialIntroduce(Observer<SpecialIntroduceResult> observer);

}
