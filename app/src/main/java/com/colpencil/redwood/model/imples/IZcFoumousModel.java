package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.ZcAllCardInfo;

import java.util.HashMap;
import java.util.Map;
import rx.Observer;

public interface IZcFoumousModel {
    void getAllFamous(Map<String,String> params);

    void subfamous(Observer<ZcAllCardInfo> observer);

    void getCareStatus(HashMap<String,String> params);

    void subCare(Observer<CareReturn> observer);
}
