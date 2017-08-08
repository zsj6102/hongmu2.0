package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.PayReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;

import okhttp3.RequestBody;
import rx.Observer;

public interface IPayModel extends ColpencilModel {
    void payInfor(HashMap<String, RequestBody> map);

    void subPay(Observer<PayReturn> subscriber);
}
