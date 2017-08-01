package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AfterSalesCenterItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.SpaceItemDecoration;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.AfterSalesCenterFragmentPresenter;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.activity.mine.WriteLogisticsActivity;
import com.colpencil.redwood.view.adapters.AfterSalesCenterAdapter;
import com.colpencil.redwood.view.impl.IAfterSalesCenterFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：售后中心
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 17 21
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_bgarefreshlayout
)
public class AfterSalesCenterFragment extends ColpencilFragment implements IAfterSalesCenterFragmentView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private String mTypeFlag;//处理类型的标识

    private AfterSalesCenterFragmentPresenter presenter;

    @Bind(R.id.bga_base)
    BGARefreshLayout bga_base;

    @Bind(R.id.rv_base)
    RecyclerView rv_base;

    private AfterSalesCenterAdapter mAdapter;
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

    private CommonDialog serviceDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTypeFlag = args.getString("key");
        }
    }

    public static AfterSalesCenterFragment getIntance(String typeFlag) {
        AfterSalesCenterFragment afterSalesCenterFragment = new AfterSalesCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", typeFlag);
        afterSalesCenterFragment.setArguments(bundle);
        return afterSalesCenterFragment;
    }

    @Override
    protected void initViews(View view) {
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
        mAdapter = new AfterSalesCenterAdapter(rv_base, mTypeFlag);
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
            public void onNext(final RxBusMsg msg) {
                if (msg.getType() == 15 && msg.getMsg().equals(mTypeFlag)) {//取消售后申请
                    serviceDialog = new CommonDialog(getActivity(), "确认取消售后?", "确定", "取消");
                    serviceDialog.setListener(new DialogOnClickListener() {
                        @Override
                        public void sureOnClick() {
                            showLoading(Constants.progressName);
                            presenter.cancelAfterSale(msg.getMap());
                            serviceDialog.dismiss();
                        }

                        @Override
                        public void cancleOnClick() {
                            serviceDialog.dismiss();
                        }
                    });
                    serviceDialog.show();
                } else if (msg.getType() == 16 && msg.getMsg().equals(mTypeFlag)) {////填写物流
                    Intent intent = new Intent(getActivity(), WriteLogisticsActivity.class);
                    intent.putExtra("key", msg.getMap().get("goodMsg"));//传入id
                    intent.putExtra("afterId", msg.getMap().get("afterId"));
                    startActivity(intent);
                } else if (msg.getType() == 17 && msg.getMsg().equals(mTypeFlag)) {//联系客服
                    if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), WebViewActivity.class);
                        intent.putExtra("key", "service");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MyWebViewActivity.class);
                        intent.putExtra("webviewurl", UrlConfig.SERVICE_URL);
                        intent.putExtra("type", "server");
                        startActivity(intent);
                    }
                } else if (msg.getType() == 36) {//刷新界面
                    showLoading(Constants.progressName);
                    mAdapter.clear();//清空数据
                    pageNo = 1;
                    presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
                }
            }
        };
        observable.subscribe(subscriber);
        if (mTypeFlag.equals("1")) {
            showLoading(Constants.progressName);
        }

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AfterSalesCenterFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<AfterSalesCenterItem> data) {
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        bga_base.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());

    }

    @Override
    public void loadMore(List<AfterSalesCenterItem> data) {
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        bga_base.endLoadingMore();
        isLoadMore(data.size());

    }

    @Override
    public void callPhone(final String phone) {
        if (serviceDialog == null) {
            serviceDialog = new CommonDialog(getActivity(), phone, "确定", "取消");
        }
        serviceDialog.show();
        serviceDialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }

            @Override
            public void cancleOnClick() {
                serviceDialog.dismiss();
            }
        });

    }

    @Override
    public void success(String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    @Override
    public void fail(int code, String msg) {
        hideLoading();
        bga_base.endLoadingMore();
        bga_base.endRefreshing(0);
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
        presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
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
            presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));

        }
        return false;
    }

    /**
     * 数据加载
     */
    @Override
    public void loadData() {
        pageNo = 1;
        presenter.getContent(pageNo, pageSize, Integer.valueOf(mTypeFlag));
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
        bga_base.endLoadingMore();
        bga_base.endRefreshing(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);

    }

}
