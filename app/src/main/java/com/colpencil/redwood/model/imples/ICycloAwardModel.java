package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @Description:百科奖励model接口
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/8/5
 */
public interface ICycloAwardModel extends ColpencilModel{
    void loadUrl();
    void sub(Observer<EntityResult<String>> observer);
}
