package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Integral;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.mine.MyIntegralPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.MyIntegralAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IMyIntegralView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.MosaicListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 曾 凤
 * @Description: 我的积分
 * @Email 20000263@qq.com
 * @date 2016/8/3
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_myintegral
)
public class MyIntegralActivity extends ColpencilActivity implements View.OnClickListener, IMyIntegralView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.list_myIntegral)
    BaseListView list_myIntegral;

    @Bind(R.id.bga_myIntegral)
    BGARefreshLayout bga_myIntegral;


    private TextView tv_pointsFor;

    private TextView myIntegral_count;

    private MyIntegralPresenter presenter;

    private MosaicListView body_listview;

    private MyIntegralAdapter mAdapter;

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

    private List<Integral> mdatas = new ArrayList<>();

    private Observable<RxBusMsg> observable;

    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        bga_myIntegral.setDelegate(this);
        bga_myIntegral.setRefreshViewHolder(new BGANormalRefreshViewHolder(MyIntegralActivity.this, true));
        bga_myIntegral.setSnackStyle(findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        View body = View.inflate(MyIntegralActivity.this, R.layout.activity_myintegral_body, null);
        myIntegral_count = (TextView) body.findViewById(R.id.myIntegral_count);
        body_listview = (MosaicListView) body.findViewById(R.id.body_listview);
        tv_pointsFor = (TextView) body.findViewById(R.id.tv_pointsFor);
        list_myIntegral.addHeaderView(body);
        list_myIntegral.setAdapter(new NullAdapter(MyIntegralActivity.this, new ArrayList<String>(), R.layout.item_null));
        initData();
    }

    private void initData() {
        tv_main_title.setText("我的积分");
        iv_back.setOnClickListener(this);
        tv_pointsFor.setOnClickListener(this);
        mAdapter = new MyIntegralAdapter(MyIntegralActivity.this, mdatas, R.layout.item_myintegral);
        body_listview.setAdapter(mAdapter);
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
                if (msg.getType() == 33) {//重新请求数据
                    showLoading(Constants.progressName);
                    mdatas.clear();
                    mAdapter.notifyDataSetChanged();
                    pageNo = 1;
                    loadData();
                }
            }
        };
        observable.subscribe(subscriber);
        showLoading(Constants.progressName);
        loadData();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyIntegralPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_pointsFor://积分兑现
                if (SharedPreferencesUtil.getInstance(this).getInt("IntegralBalance", 0) == 0) {
                    ColpenciSnackbarUtil.downShowing(MyIntegralActivity.this.findViewById(android.R.id.content), "暂无积分可兑换");
                } else {
                    Intent intent = new Intent(MyIntegralActivity.this, GetCouponsActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void loadIntegralData(List<Integral> data, int count) {
        myIntegral_count.setText(count + "");
        mdatas.addAll(data);
        mAdapter.notifyDataSetChanged();
        bga_myIntegral.endLoadingMore();
        bga_myIntegral.endRefreshing(0);
        isLoadMore(data.size());

    }

    @Override
    public void fail(String code, String msg) {
        bga_myIntegral.endLoadingMore();
        bga_myIntegral.endRefreshing(0);
        hideLoading();
        ColpenciSnackbarUtil.downShowing(MyIntegralActivity.this.findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(MyIntegralActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 数据加载
     */
    private void loadData() {
        presenter.getContent(pageNo, pageSize);
        pageNo++;
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
        bga_myIntegral.endLoadingMore();
        bga_myIntegral.endRefreshing(0);
        hideLoading();
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoading(Constants.progressName);
        mdatas.clear();
        mAdapter.notifyDataSetChanged();
        pageNo = 1;
        loadData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            showLoading(Constants.progressName);
            loadData();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);

    }
}
