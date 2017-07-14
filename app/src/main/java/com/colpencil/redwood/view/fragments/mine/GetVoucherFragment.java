package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Coupon;
import com.colpencil.redwood.bean.CouponsResult;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.mine.MyCouponsFragmentPresenter;
import com.colpencil.redwood.view.adapters.MyCouponsFragmentAdapter;
import com.colpencil.redwood.view.impl.IMyCouponsFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 陈 宝
 * @Description:代金券
 * @Email 1041121352@qq.com
 * @date 2016/10/18
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_voucher
)
public class GetVoucherFragment extends ColpencilFragment implements IMyCouponsFragmentView {

    @Bind(R.id.bga_base)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.rv_base)
    GridView gridView;
    @Bind(R.id.ll_header)
    LinearLayout ll_header;

    private MyCouponsFragmentPresenter presenter;
    private MyCouponsFragmentAdapter mAdapter;
    private List<Coupon> mdata = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        ll_header.setVisibility(View.GONE);
        mAdapter = new MyCouponsFragmentAdapter(getActivity(), mdata, R.layout.item_voucher, 0, 1);
        gridView.setAdapter(mAdapter);
        showLoading(Constants.progressName);
        loadData();
        mAdapter.setListener(new MyCouponsFragmentAdapter.ChangeListener() {
            @Override
            public void change(int position) {
                Coupon coupon = mdata.get(position);
                showLoading(Constants.progressName);
                presenter.change(coupon.getPoint(), coupon.getCpns_sn(), coupon.getCpns_id());
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyCouponsFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadResult(CouponsResult result) {
        hideLoading();
        mdata.clear();
        mdata.addAll(result.getResult().getVoucherList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void fail(String code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    @Override
    public void changeResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(33);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            presenter.loadGetCoupon();
        }
        ToastTools.showShort(getActivity(), result.getMessage());
    }

    /**
     * 数据加载
     */
    private void loadData() {
        presenter.loadGetCoupon();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
