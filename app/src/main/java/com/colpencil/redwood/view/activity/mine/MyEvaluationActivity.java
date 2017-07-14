package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Comment;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.function.utils.SpaceItemDecoration;
import com.colpencil.redwood.present.mine.MyEvaluationPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.MyEvaluationAdapter;
import com.colpencil.redwood.view.impl.IMyEvaluationView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.List;

import butterknife.Bind;


/**
 * 描述：我的评价
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/1 12 02
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_recyclerview
)
public class MyEvaluationActivity extends ColpencilActivity implements IMyEvaluationView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.activity_bga_base)
    BGARefreshLayout activity_bga_base;
    @Bind(R.id.activity_rv_base)
    RecyclerView activity_rv_base;
    private MyEvaluationPresenter presenter;
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
    private MyEvaluationAdapter mAdapter;

    @Override
    protected void initViews(View view) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.padding_10dp);
        activity_rv_base.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        activity_bga_base.setDelegate(this);
        activity_bga_base.setRefreshViewHolder(new BGANormalRefreshViewHolder(MyEvaluationActivity.this, true));
        activity_bga_base.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new MyEvaluationAdapter(activity_rv_base);
        activity_rv_base.setLayoutManager(new LinearLayoutManager(MyEvaluationActivity.this, LinearLayoutManager.VERTICAL, false));
        activity_rv_base.setAdapter(mAdapter);
        tv_main_title.setText("我的评价");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showLoading(Constants.progressName);
        loadData();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyEvaluationPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<Comment> data) {
        mAdapter.addNewDatas(data);//添加最新数据
        mAdapter.notifyDataSetChanged();//适配器进行刷新操作
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<Comment> data) {
        mAdapter.addMoreDatas(data);//增加新数据
        mAdapter.notifyDataSetChanged();
        isLoadMore(data.size());
    }

    @Override
    public void fail(String code, String msg) {
        hideLoading();
        activity_bga_base.endLoadingMore();
        activity_bga_base.endRefreshing(0);
        ColpenciSnackbarUtil.downShowing(MyEvaluationActivity.this.findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(MyEvaluationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
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
        loadData();
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
            loadData();
        }
        return false;
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
        hideLoading();
        activity_bga_base.endLoadingMore();
        activity_bga_base.endRefreshing(0);
    }

}
