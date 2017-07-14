package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public interface IAllSpecialItemModel extends ColpencilModel {
    /**
     * 获取所有的专场信息
     *
     */
    void getSpecial(HashMap<String,Integer> intparams, HashMap<String, RequestBody> params);

    void subGetSpecial(Observer<AllSpecialResult> observer);
}
