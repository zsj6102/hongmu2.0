package com.colpencil.redwood.present.mine;

import com.colpencil.redwood.bean.Address;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.model.AddAddressModel;
import com.colpencil.redwood.model.imples.IAddAddressModel;
import com.colpencil.redwood.view.impl.IAddAdressView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;

import rx.Observer;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 10 34
 */
public class AddAddressPresenter extends ColpencilPresenter<IAddAdressView> {

    private IAddAddressModel addressModel;

    public AddAddressPresenter() {
        addressModel = new AddAddressModel();
    }

    //添加
    public void addAddress(Address address) {
        addressModel.addAddress(address);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.result(result);
            }
        };
        addressModel.subAdd(observer);
    }

    //更新
    public void updateAddress(Address address) {
        addressModel.updateAddress(address);
        Observer<EntityResult<String>> observer = new Observer<EntityResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(EntityResult<String> result) {
                mView.result(result);
            }
        };
        addressModel.subUpdate(observer);
    }

}

