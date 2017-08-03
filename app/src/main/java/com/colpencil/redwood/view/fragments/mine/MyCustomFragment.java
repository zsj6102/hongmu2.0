package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Custom;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.Pay.LhjalipayUtils;
import com.colpencil.redwood.function.utils.Pay.Wechat.WeChatPayForUtil;
import com.colpencil.redwood.function.utils.SpaceItemDecoration;
import com.colpencil.redwood.present.mine.MyCustomFragmentPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.PaymentActivity;
import com.colpencil.redwood.view.activity.discovery.CustomActionActivity;
import com.colpencil.redwood.view.adapters.MyCustomAdapter;
import com.colpencil.redwood.view.impl.IMyCustomFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.unionpay.UPPayAssistEx;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：我的定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 17 21
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_bgarefreshlayout
)
public class MyCustomFragment extends ColpencilFragment implements IMyCustomFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private String mTypeFlag;//处理类型的标识

    private MyCustomFragmentPresenter presenter;

    @Bind(R.id.bga_base)
    BGARefreshLayout bga_base;

    @Bind(R.id.rv_base)
    RecyclerView rv_base;

    private MyCustomAdapter mAdapter;
    /**
     * 分页请求页码
     */
    private int pageNo = 1;
    /**
     * 每页信息条数
     */
    private int pageSize = 10;
    /**
     * 是否可进行上拉加载操作
     */
    private boolean flag;

    private Observable<RxBusMsg> observable;

    private Subscriber subscriber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTypeFlag = args.getString("key");
        }
    }

    public static MyCustomFragment getIntance(String typeFlag) {
        MyCustomFragment myCustomFragment = new MyCustomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", typeFlag);
        myCustomFragment.setArguments(bundle);
        return myCustomFragment;
    }

    @Override
    protected void initViews(View view) {
        if (mTypeFlag.equals("0")) {
            showLoading(Constants.progressName);
        }
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        rv_base.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        bga_base.setDelegate(this);
        bga_base.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_base.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new MyCustomAdapter(rv_base, mTypeFlag);
        rv_base.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_base.setAdapter(mAdapter);
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 20 && msg.getMsg().equals(mTypeFlag)) {//进行相关支付操作
                    if (msg.getCustomType() == 0 && msg.getStateName() == 1) {//私人定制并且未进行过第一次付款操作
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        intent.putExtra("key", "订单确认");
                        intent.putExtra("goFrom", "MyCustomFragment");
                        intent.putExtra("product_id", msg.getProduct_id());
                        intent.putExtra("goods_id", msg.getGoods_id());
                        intent.putExtra("cm_id",msg.getCpns_id());
                        startActivity(intent);
                    } else {//官方定制
                        showLoading(Constants.progressName);
                        presenter.payKeyInfor(Integer.valueOf(msg.getOrderNum()));
                    }

                } else if (msg.getType() == 21 && msg.getMsg().equals(mTypeFlag)) {//跳转到私人定制界面
                    Intent intent = new Intent(getActivity(), CustomActionActivity.class);
                    startActivity(intent);
                } else if (msg.getType() == 50) {//刷新列表
                    mAdapter.clear();//清空数据
                    pageNo = 1;
                    if (mTypeFlag.equals("0")) {
                        showLoading(Constants.progressName);
                    }
                    presenter.getContent(pageNo, pageSize, mTypeFlag);
                }
            }
        };
        observable.subscribe(subscriber);

        mAdapter.setListener(new MyCustomAdapter.OnMyItemClickListener() {
            @Override
            public void itemClick(int position) {

            }

            @Override
            public void delete(int position) {
                showLoading("正在删除中");
                presenter.delete(mAdapter.getItem(position).getCm_id());
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyCustomFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<Custom> data) {
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        bga_base.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<Custom> data) {
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        bga_base.endLoadingMore();
        isLoadMore(data.size());

    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        bga_base.endRefreshing(0);
        bga_base.endLoadingMore();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoading(Constants.progressName);
        mAdapter.clear();//清空数据
        pageNo = 1;
        presenter.getContent(pageNo, pageSize, mTypeFlag);
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            showLoading(Constants.progressName);
            pageNo++;
            presenter.getContent(pageNo, pageSize, mTypeFlag);
        }
        return false;
    }

    /**
     * 数据加载
     */
//    private void loadData() {
//        presenter.getContent(pageNo, pageSize, mTypeFlag);
//        pageNo++;
//    }
    @Override
    public void loadData() {
        presenter.getContent(pageNo, pageSize, mTypeFlag);
    }
    /**
     * 设置是否可进行上拉加载操作
     */
    private void isLoadMore(int size) {
        if (size < pageSize) {
            flag = false;
        } else {
            flag = true;
        }
        hideLoading();
        bga_base.endRefreshing(0);
        bga_base.endLoadingMore();
    }

    @Override
    public void payByWeChat(Map<String, String> map) {
        hideLoading();
        new WeChatPayForUtil(getActivity(), map);

    }

    @Override
    public void payByAlipay(String reStr) {
        hideLoading();
        LhjalipayUtils.getInstance(getActivity()).pay(reStr);
    }

    @Override
    public void payByUnion(String tn, String mode) {
        hideLoading();
        UPPayAssistEx.startPay(getActivity(), null, null, tn, mode);
    }

    @Override
    public void delete(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            mAdapter.clear();//清空数据
            RxBusMsg msg = new RxBusMsg();
            msg.setType(50);
            RxBus.get().post("rxBusMsg", msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);      //处理银联返回结果
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            ToastTools.showShort(getActivity(), "银联支付成功");
        } else {
            ToastTools.showShort(getActivity(), "银联支付失败");
        }
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }
}
