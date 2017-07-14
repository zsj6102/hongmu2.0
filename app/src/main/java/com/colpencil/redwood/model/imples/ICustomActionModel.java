package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/8 11 28
 */
public interface ICustomActionModel extends ColpencilModel {
    /**
     * 描述 界面加载后获取的信息
     */
    void loadData(String acid);

    void subBanner(Observer<ListResult<BannerVo>> observer);

    /**
     * 描述  提交定制申请
     */
    void sumbitCustom(String contact, String mobile, String qq, String wechat, String description, List<File> files);

    void subCustom(Observer<EntityResult<String>> observer);
}
