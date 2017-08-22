package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.Info.StoreDetail;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;
import java.util.Map;

import rx.Observer;

public interface IAboutModel extends ColpencilModel {
    void loadinfo(Map<String,String> params);

    void subInfo(Observer<ResultInfo<StoreDetail>> observer);
    /**
     * 关注
     * @param
     */
    void lodaLike(Map<String,String> params);


    void subLike(Observer<ResultInfo<List<ItemStoreFans>>> observer);

    /**
     * 粉丝
     * @param params
     */
    void loadFans(Map<String,String> params);

    void subFans(Observer<ResultInfo<List<ItemStoreFans>>> observer);

    /**
     * 关注商家
     */

    void care(Map<String,String> parems);

    void subCare(Observer<CareReturn> observer);

    /**
     * 取消关注商家
     */

    void unCare(Map<String,String> params);

    void subUnCare(Observer<CareReturn> observer);
    /**
     * 商家关注和粉丝  相关
     */
    void  storeCare(Map<String,String> params);

    void subUnstoreCare(Observer<CareReturn> observer);
}
