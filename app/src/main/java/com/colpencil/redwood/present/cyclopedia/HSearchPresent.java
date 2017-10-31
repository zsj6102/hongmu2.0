package com.colpencil.redwood.present.cyclopedia;

import com.colpencil.redwood.bean.CatReturnData;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.model.HSearchModel;
import com.colpencil.redwood.model.imples.IHSearchModel;
import com.colpencil.redwood.view.impl.IHSearchView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import rx.Observer;

/**
 * 描述：纵向搜索网络请求
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 17 03
 */
public class HSearchPresent extends ColpencilPresenter<IHSearchView>{
    private IHSearchModel hSearchModel;

    public HSearchPresent() {
        hSearchModel = new HSearchModel();
    }

    /**
     * 获取商品总分类信息
     */
    public void loadListViewData() {
        hSearchModel.loadListViewData();
        Observer<CatReturnData> observer = new Observer<CatReturnData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(mView!=null){
                    mView.fail("服务请求失败");
                }

            }

            @Override
            public void onNext(CatReturnData catReturnData) {
                if(catReturnData.getCode().equals("0")){//信息请求成功
                    if(mView!=null){
                        mView.loadListViewData(catReturnData.getResult());
                        RxBusMsg rxBusMsg=new RxBusMsg();
                        rxBusMsg.setMsg("CatReturnDataSuccess");
                        RxBus.get().post("rxBusMsg",rxBusMsg);
                    }

                }else{
                    if(mView!=null) {
                        mView.fail(catReturnData.getMessage());
                    }
                }
            }
        };
        hSearchModel.subListView(observer);
    }

}
