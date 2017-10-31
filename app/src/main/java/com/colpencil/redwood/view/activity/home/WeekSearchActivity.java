package com.colpencil.redwood.view.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.WeekAuctionList;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.WeekShootPresenter;
import com.colpencil.redwood.view.adapters.WeekShootAdapter;
import com.colpencil.redwood.view.impl.IWeekShootView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

import static com.colpencil.redwood.R.id.refreshLayout;
import static com.colpencil.redwood.view.activity.HomeActivity.result;

@ActivityFragmentInject(
        contentViewId = R.layout.week_search_layout)
public class WeekSearchActivity extends ColpencilActivity implements IWeekShootView,BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.base_header_edit)
    EditText editText;
    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    @Bind(R.id.bga_weekShoot)
    BGARefreshLayout bga_weekShoot;
    @Bind(R.id.rv_weekShoot)
    RecyclerView rv_weekShoot;
    private String keyword;
    private WeekShootPresenter weekShootPresenter;
    private WeekShootAdapter mAdapter;
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
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        keyword = getIntent().getStringExtra("keyword");
        editText.setHint("搜索周拍");
        editText.setText(keyword);
        bga_weekShoot.setDelegate(this);
        refreshLayout2.setDelegate(this);
        bga_weekShoot.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        bga_weekShoot.setSnackStyle(this.findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout2.setSnackStyle(this.findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
        initBus();
    }
    @Override
    public void refresh(List<WeekAuctionList> data) {
        hideLoading();
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //停止刷新
        bga_weekShoot.endRefreshing(data.size());
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
        if (ListUtils.listIsNullOrEmpty(data)) {
            refreshLayout2.setVisibility(View.VISIBLE);
            bga_weekShoot.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            bga_weekShoot.setVisibility(View.VISIBLE);
        }
    }
    private void initData() {
        mAdapter = new WeekShootAdapter(rv_weekShoot,this);
        rv_weekShoot.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_weekShoot.setAdapter(mAdapter);
        weekShootPresenter.getSearch(keyword, pageNo, pageSize);
        showLoading(Constants.progressName);
    }

    private void initBus() {
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
                if (msg.getType() == 65) {
                    pageNo = 1;
                    weekShootPresenter.getSearch(keyword, pageNo, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }
    @Override
    public void loadMore(List<WeekAuctionList> data) {
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        bga_weekShoot.endLoadingMore();
        isLoadMore(data.size());
    }
    private void isLoadMore(int size) {
        if (size < pageSize) {
            flag = false;
        } else {
            flag = true;
        }
        refreshLayout2.endRefreshing(0);
        bga_weekShoot.endRefreshing(0);
        bga_weekShoot.endLoadingMore();
        refreshLayout2.endLoadingMore();
    }
    @Override
    public void loadError(String message) {
        hideLoading();
        ToastTools.showShort(this,message);

        refreshLayout2.endRefreshing(0);
        bga_weekShoot.endRefreshing(0);
        bga_weekShoot.endLoadingMore();
        refreshLayout2.endLoadingMore();
        refreshLayout2.setVisibility(View.VISIBLE);
        bga_weekShoot.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }
    @OnClick(R.id.base_header_search)
    void search() {
        keyword = editText.getText().toString();
        pageNo =1;
        showLoading(Constants.progressName);
        weekShootPresenter.getSearch(keyword, pageNo, pageSize);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(keyword)) {
                    DaoUtils.saveHistory(11, keyword, WeekSearchActivity.this);
                }
            }
        }).start();
    }
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mAdapter.clear();//清空数据
        pageNo = 1;
        weekShootPresenter.getSearch(keyword, pageNo, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            pageNo++;
            weekShootPresenter.getSearch(keyword, pageNo, pageSize);
        }
        return false;
    }

    @Override
    public ColpencilPresenter getPresenter() {
        weekShootPresenter = new WeekShootPresenter();
        return weekShootPresenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
