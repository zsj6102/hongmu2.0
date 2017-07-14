package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description: 填写物流
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public interface IWriteLogisticsModel extends ColpencilModel {

    void sumbit(HashMap<String, String> map);

    //注册观察者
    void subResult(Observer<ResultCodeInt> subscriber);

    /**
     * 获取物流公司
     */
    void loadLogisTics(int item_id, String order_id);

    void subLogistic(Observer<ListResult<LogisTicsBean>> observer);
}