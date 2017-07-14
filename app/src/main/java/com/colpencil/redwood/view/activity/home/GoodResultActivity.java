package com.colpencil.redwood.view.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.home.SearchGoodPresenter;
import com.colpencil.redwood.view.adapters.HomeGoodListAdapter;
import com.colpencil.redwood.view.impl.ISearchGoodView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:搜索商品的结果界面
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search_good)
public class GoodResultActivity extends ColpencilActivity implements ISearchGoodView, BGARefreshLayoutDelegate {

    @Bind(R.id.search_recycler)
    GridView gridview;
    @Bind(R.id.base_header_edit)
    EditText editText;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    private SearchGoodPresenter presenter;
    private String keyword;
    private HomeGoodListAdapter adapter;
    private List<HomeGoodInfo> goodList = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        keyword = getIntent().getStringExtra("keyword");
        editText.setHint("搜索商品");
        editText.setText(keyword);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new HomeGoodListAdapter(this, goodList, R.layout.item_home_good);
        gridview.setAdapter(adapter);
        showLoading(Constants.progressName);
        if (TextUtils.isEmpty(keyword)) {
            keyword = "";
        }
        presenter.loadGood(keyword, page, pageSize);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SearchGoodPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void loadData() {
        presenter.loadGood(keyword, page, pageSize);
        //保存搜索结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(keyword)) {
                    DaoUtils.saveHistory(7, keyword, GoodResultActivity.this);
                }
            }
        }).start();
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.base_header_search)
    void search() {
        keyword = editText.getText().toString();
        loadData();
    }

    @Override
    public void refresh(List<HomeGoodInfo> list) {
        hideLoading();
        isLoadMore(list);
        goodList.clear();
        goodList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<HomeGoodInfo> list) {
        isLoadMore(list);
        goodList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError() {
        hideLoading();
        refreshLayout.endRefreshing(0);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadGood(keyword, page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadGood(keyword, page, pageSize);
        }
        return false;
    }

    private void isLoadMore(List<HomeGoodInfo> result) {
        if (!ListUtils.listIsNullOrEmpty(result)) {
            if (result.size() < pageSize) {
                isRefresh = false;
            } else {
                isRefresh = true;
            }
            refreshLayout.endRefreshing(result.size());
        } else {
            isRefresh = false;
            refreshLayout.endRefreshing(0);
        }
    }
}
