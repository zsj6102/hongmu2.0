package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:标签分类
 * @Email DramaScript@outlook.com
 * @date 2016/8/11
 */
public interface ICategoryModel extends ColpencilModel {

    /**
     * 获取所有标签
     */
    void loadAllTag();

    void subAllTag(Observer<ListResult<CategoryVo>> observer);

    /**
     * 获取所有标签
     */
    void loadMyTag();

    void subMyTag(Observer<ListResult<CategoryVo>> observer);

    /**
     * 保存我的标签
     *
     * @param cat_type
     * @param list
     */
    void addTagToServer(int cat_type, List<String> list);

    void subSubmit(Observer<EntityResult<String>> observer);

    /**
     * 周拍
     */
    void loadWeekShoot();

    void subWeekShoot(Observer<ListResult<CategoryVo>> observer);

    /**
     * 周拍
     */
    void loadAllWeekShoot();

    void subAllWeekShoot(Observer<ListResult<CategoryVo>> observer);

    /**
     * 保存我的周拍分类
     *
     * @param cat_id
     */
    void saveWeekTag(String cat_id);

    void subSaveWeekTag(Observer<EntityResult<String>> observer);

    /**
     * 藏友圈
     */
    void loadCircle();

    void subCircle(Observer<ListResult<CategoryVo>> observer);

    /**
     * 藏友圈
     */
    void loadAllCircle();

    void subAllCircle(Observer<ListResult<CategoryVo>> observer);

    /**
     * 藏友圈
     *
     * @param cat_id
     */
    void saveCircle(String cat_id);

    void subSaveCircle(Observer<EntityResult<String>> observer);
}
