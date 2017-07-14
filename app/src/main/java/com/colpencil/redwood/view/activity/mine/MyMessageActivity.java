package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.Massage;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Result;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.mine.MyMessagePresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.MyMessageAdapter;
import com.colpencil.redwood.view.impl.IMessageView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：我的消息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/1 12 02
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_mymessage
)
public class MyMessageActivity extends ColpencilActivity implements IMessageView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;
    @Bind(R.id.bga_MyMessage)
    BGARefreshLayout activity_bga_base;
    @Bind(R.id.base_MyMessage)
    ListView listView;
    private MyMessagePresenter presenter;

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
    private MyMessageAdapter adapter;
    private List<Massage> mData = new ArrayList<>();
    private boolean locked;

    @Override
    protected void initViews(View view) {
        activity_bga_base.setDelegate(this);
        activity_bga_base.setRefreshViewHolder(new BGANormalRefreshViewHolder(MyMessageActivity.this, true));
        activity_bga_base.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        adapter = new MyMessageAdapter(MyMessageActivity.this, mData, R.layout.item_mymessage);
        listView.setAdapter(adapter);
        tv_main_title.setText("消息中心");
        tv_shoppingCartFinish.setText("清除");
        showLoading(Constants.progressName);
        loadData();
        adapter.setListener(new MyMessageAdapter.DeleteListener() {
            @Override
            public void delete(int position) {
                showLoading("正在删除中...");
                presenter.removeById(mData.get(position).getId());
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyMessagePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadData(List<Massage> data) {
        hideLoading();
        if (pageNo == 1) {
            mData.clear();
        }
        mData.addAll(data);
        adapter.notifyDataSetChanged();
        activity_bga_base.endRefreshing(0);
        activity_bga_base.endLoadingMore();
        isLoadMore(data.size());
        locked = false;
    }

    @Override
    public void delete(Result result) {
        if (result.getCode().equals("1")) {
            pageNo = 1;
            loadData();
        }
    }

    @Override
    public void resultInfor(String code, String msg) {
        locked = false;
        activity_bga_base.endRefreshing(0);
        activity_bga_base.endLoadingMore();
        hideLoading();
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(MyMessageActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            pageNo = 1;
            presenter.getContent(pageNo, pageSize);
        }
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
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
            pageNo++;
            loadData();
        }
        return false;
    }

    /**
     * 数据加载
     */
    private void loadData() {
        presenter.getContent(pageNo, pageSize);
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
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.tv_shoppingCartFinish)
    void delete() {
        if (SharedPreferencesUtil.getInstance(this).getInt("MsgCount", 0) == 0) {
            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "暂无相关消息");
        } else if (locked == false) {
            showLoading("正在删除");
            locked = true;
            presenter.removeAllMsg();
        }
    }

}
