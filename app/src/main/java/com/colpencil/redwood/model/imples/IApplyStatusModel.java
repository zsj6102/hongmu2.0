package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.ResultInfo;

import java.util.HashMap;

import rx.Subscriber;

public interface IApplyStatusModel {
    void applyStatus(  HashMap<String,String> params);
    void subStauts(Subscriber<ApplyStatusReturn> subscriber);

    void getHotLine();

    void subHotLine(Subscriber<ResultInfo<String>> subscriber);
}
