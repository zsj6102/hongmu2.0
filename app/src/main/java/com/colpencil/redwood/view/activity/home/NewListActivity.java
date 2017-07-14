package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.NewsInfoVo;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.present.home.NewsListPresenter;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.adapters.NewsListAdapter;
import com.colpencil.redwood.view.impl.INewsListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:新闻动态列表
 * @Email DramaScript@outlook.com
 * @date 2016/7/25
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_news_list)
public class NewListActivity extends ColpencilActivity implements INewsListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.base_header_ll)
    LinearLayout ll_header;
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.common_listview)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private NewsListAdapter adapter;
    private List<NewsInfoVo> mList = new ArrayList<>();
    private NewsListPresenter presenter;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private Observable<GoodBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        ll_header.setBackgroundColor(getResources().getColor(R.color.main_background));
        tv_title.setText(getString(R.string.tv_news_moving));
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new NewsListAdapter(this, mList, R.layout.item_second_cyclopedia);
        listView.setAdapter(adapter);
        presenter.loadNewsList("8", page, pageSize);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewListActivity.this, CyclopediaDetailActivity.class);
                CycloParams params = new CycloParams();
                params.article_id = adapter.getItem(position).getArticle_id() + "";
                params.type = "news";
                params.title = adapter.getItem(position).getPage_title();
                params.content = adapter.getItem(position).getPage_description();
                params.image = adapter.getItem(position).getImage();
                params.time = adapter.getItem(position).getAdd_time();
                intent.putExtra("params", params);
                startActivity(intent);
            }
        });
        initBus();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new NewsListPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void initBus() {
        observable = RxBus.get().register(StringConfig.GOODSBUS, GoodBusMsg.class);
        subscriber = new Subscriber<GoodBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GoodBusMsg msg) {
                if (msg.getType().equals("refresh")) {
                    page = 1;
                    presenter.loadNewsList("8", page, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.common_float)
    void postOnClick() {
        Intent intent = new Intent();
        intent.setClass(this, PostNewsActivity.class);
        intent.putExtra("type", "news");
        startActivity(intent);
    }

    @OnClick(R.id.iv_totop)
    void topOnClick() {
        listView.setSelection(0);
    }

    @Override
    public void refresh(List<NewsInfoVo> list) {
        isLoadMore(list);
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<NewsInfoVo> list) {
        isLoadMore(list);
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String msg) {
        ToastTools.showShort(this, msg);
    }

    private void isLoadMore(List<NewsInfoVo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadNewsList("8", page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadNewsList("8", page, pageSize);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(StringConfig.GOODSBUS, observable);
    }
}
