package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * Created by xiaobao on 2016/10/30.
 */
public interface ICollectionModel extends ColpencilModel {

    /**
     * 分享
     *
     * @param ote_id
     */
    void share(int ote_id);

    void subShare(Observer<CommonResult> observer);

    /**
     * 记录分享
     *
     * @param ote_id
     */
    void recordShare(int ote_id, String platform);

    void subRecord(Observer<EntityResult<String>> observer);

}
