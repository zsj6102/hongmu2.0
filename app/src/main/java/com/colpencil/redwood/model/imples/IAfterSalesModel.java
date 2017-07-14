package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import rx.Observer;

/**
 * @Description: 售后
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/10
 */
public interface IAfterSalesModel extends ColpencilModel {


    void loadReason(int type);

    /**
     * 提交退款申请
     */

    void sumbit(HashMap<String, String> map, List<File> files);

    //注册观察者
    void subResult(Observer<ResultCodeInt> subscriber);
}