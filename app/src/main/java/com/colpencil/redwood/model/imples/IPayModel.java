package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.PayReturn;
import com.colpencil.redwood.bean.PayType;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;

public interface IPayModel extends ColpencilModel {
    void payInfor(HashMap<String, RequestBody> map);

    void subPay(Observer<PayReturn> subscriber);

    void payType();

    void subType(Observer<ResultInfo<List<PayType>>> observer);


    void centerPay(Map<String,String> map);

    void subCenter(Observer<PayReturn> subscriber);

    void marginPay(Map<String,String> map);

    void subMargin(Observer<PayReturn> subscriber);

    void settledPay(Map<String,String> map);

    void subSettled(Observer<PayReturn> observer);

}
