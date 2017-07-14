package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:百科列表的model接口
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public interface ICycloListModel extends ColpencilModel {

    /**
     * 获取百科
     *
     * @param operationType
     * @param pageNo
     * @param pageSize
     */
    void loadList(String operationType, int pageNo, int pageSize);

    void sub(Observer<ListResult<MyCyclopediaInfo>> observer);

    /**
     * 获取新闻
     *
     * @param operationType
     * @param pageNo
     * @param pageSize
     */
    void loadNews(String operationType, int pageNo, int pageSize);

    void subNews(Observer<ListResult<MyCyclopediaInfo>> observer);

    /**
     * 取消审核
     *
     * @param article_id
     */
    void cancleAudit(int article_id);

    void subCancle(Observer<EntityResult<String>> observer);

    /**
     * 删除
     *
     * @param article_id
     */
    void delete(int article_id);

    void subDelete(Observer<EntityResult<String>> observer);
}
