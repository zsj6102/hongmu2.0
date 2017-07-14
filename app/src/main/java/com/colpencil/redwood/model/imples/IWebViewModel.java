package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ResultCodeInt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 曾 凤
 * @Description: 我的积分
 * @Email 20000263@qq.com
 * @date 2016/8/3
 */
public interface IWebViewModel extends ColpencilModel {

    //获取数据
    void logisticsInfor(int orderId);


    //注册观察者
    void sub(Observer<ResultCodeInt> subscriber);

    void getH5Url(int type);

    /**
     * 请求相关规则
     */
    void loadRule();

    void subRule(Observer<EntityResult<String>> observer);

    /**
     * 请求客服接口
     */
    void loadService();

    void subService(Observer<EntityResult<String>> observer);

    /**
     * 请求等级信息
     */
    void loadInfo();

    void subInfo(Observer<EntityResult<String>> observer);
}
