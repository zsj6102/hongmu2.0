package com.colpencil.redwood.present.home;

import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.model.CategoryModel;
import com.colpencil.redwood.model.imples.ICategoryModel;
import com.colpencil.redwood.view.impl.ICategoryView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

/**
 * @author 陈宝
 * @Description:分类标签
 * @Email DramaScript@outlook.com
 * @date 2016/8/11
 */
public class CategoryPresenter extends ColpencilPresenter<ICategoryView> {

    private ICategoryModel model;

    public CategoryPresenter() {
        model = new CategoryModel();
    }

    /**
     * 获取我的标签
     */
    public void loadMyTag() {
        model.loadMyTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
                    if (result.getCode() == 0) {
                        mView.loadMyTag(result.getMemberCatListResult());
                    } else {
                        mView.loadMyTag(new ArrayList<CategoryVo>());
                    }
                } else {
                    mView.loadMyTag(new ArrayList<CategoryVo>());
                }
            }
        };
        model.subMyTag(observer);
    }

    /**
     * 获取所有表情
     */
    public void loadAllTag() {
        model.loadAllTag();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> homeTagResult) {
                if (homeTagResult.getCode() == 0) {
                    mView.loadAllTag(homeTagResult.getCatListResult());
                }
            }
        };
        model.subAllTag(observer);
    }

    public void addToServer(int cat_type, List<String> list) {
        model.addTagToServer(cat_type, list);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.operateResult(result);
            }
        };
        model.subSubmit(observer);
    }

    /**
     * 周拍
     */
    public void loadWeekShoot() {
        model.loadWeekShoot();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 1) {
                    mView.loadMyTag(result.getResult());
                }
            }
        };
        model.subWeekShoot(observer);
    }

    public void loadAllWeekTag() {
        model.loadAllWeekShoot();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 1) {
                    mView.loadAllTag(result.getResult());
                }
            }
        };
        model.subAllWeekShoot(observer);
    }

    public void saveWeekTag(String cat_id) {
        model.saveWeekTag(cat_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.operateResult(result);
            }
        };
        model.subSaveWeekTag(observer);
    }

    /**
     * 藏友圈
     */
    public void loadCircle() {
        model.loadCircle();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 0) {
                    mView.loadMyTag(result.getCatListResult());
                }
            }
        };
        model.subCircle(observer);
    }

    public void loadAllCircle() {
        model.loadAllCircle();
        Observer<ListResult<CategoryVo>> observer = new Observer<ListResult<CategoryVo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ListResult<CategoryVo> result) {
                if (result.getCode() == 0) {
                    mView.loadAllTag(result.getCatListResult());
                }
            }
        };
        model.subAllCircle(observer);
    }

    public void saveCircle(String cat_id) {
        model.saveCircle(cat_id);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.operateResult(result);
            }
        };
        model.subSaveCircle(observer);
    }
}
