package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IFansAndLikeModel extends ColpencilModel {
    void loadLikeAndFans(Map<String,String> map);

    void subFansLike(Observer<ResultInfo<List<ItemStoreFans>>> observer);


    void getCareStatus(HashMap<String,String> params);

    void subCare(Observer<CareReturn> observer);

    void loadSearch(Map<String,String> map);

    void subSearch(Observer<ResultInfo<List<ItemStoreFans>>> observer);

    void getHotFans(Map<String,String> map);

    void subHotFans(Observer<ResultInfo<List<ItemStoreFans>>> observer);
}
