package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;

import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;

import com.colpencil.redwood.holder.HolderFactory;
import com.colpencil.redwood.holder.home.GoodHeadViewHolder;
import com.colpencil.redwood.holder.home.GoodViewHolder;
import com.colpencil.redwood.holder.home.MiddleItemViewHolder;
import com.colpencil.redwood.holder.home.TopBannerViewHolder;
import com.colpencil.redwood.holder.home.ViewpagerGridViewHolder;

import com.colpencil.redwood.present.home.HomePresenter;

import com.colpencil.redwood.view.activity.home.MyWebViewActivity;

import com.colpencil.redwood.view.activity.mine.WebViewActivity;


import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IHomePageView;

import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * @author 陈 宝
 * @Description:首页
 * @Email 1041121352@qq.com
 * @date 2016/9/22
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_homepage)
public class HomePageFragment extends ColpencilFragment implements IHomePageView, BGARefreshLayoutDelegate {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.home_recyclerView)
    ListView listView;
    private HomePresenter presenter;
    private boolean isRefresh = false;
    private int page = 1;
    private int pageSize = 10;
    private TopBannerViewHolder topBanner;
    private ViewpagerGridViewHolder funcHolder;
    private MiddleItemViewHolder middle1;
    private MiddleItemViewHolder middle2;
    private MiddleItemViewHolder middle3;
    private MiddleItemViewHolder middle4;
    private MiddleItemViewHolder middle5;
    private TopBannerViewHolder middleBanner;
    private GoodViewHolder goodHolder;
    private GoodHeadViewHolder headHolder;
    private List<HomeGoodInfo> goods = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        initHeader();
        initHolder();
    }

    private void initHeader() {

        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }

    @Override
    public void loadData() {
        presenter.loadRecommend();
        presenter.loadGoods("20", page, pageSize);
        showLoading("");
    }

    private void initHolder() {

        topBanner = new TopBannerViewHolder(0, getActivity());
        funcHolder = new ViewpagerGridViewHolder(1, getActivity());
        middle1 = HolderFactory.createHolder(0, getActivity());
        middle2 = HolderFactory.createHolder(1, getActivity());
        middle3 = HolderFactory.createHolder(2, getActivity());
        middle4 = HolderFactory.createHolder(3, getActivity());
        middle5 = HolderFactory.createHolder(4, getActivity());
        middleBanner = new TopBannerViewHolder(7, getActivity());
        headHolder = new GoodHeadViewHolder(8, getActivity());
        goodHolder = new GoodViewHolder(9, getActivity());
        listView.addHeaderView(topBanner.getContentView());
        listView.addHeaderView(funcHolder.getContentView());
        listView.addHeaderView(middle1.getContentView());
        listView.addHeaderView(middle2.getContentView());
        listView.addHeaderView(middle3.getContentView());
        listView.addHeaderView(middle4.getContentView());
        listView.addHeaderView(middle5.getContentView());
        listView.addHeaderView(middleBanner.getContentView());
        listView.addHeaderView(headHolder.getContentView());
        listView.addHeaderView(goodHolder.getContentView());
        listView.setAdapter(new NullAdapter(getActivity(), new ArrayList<String>(), R.layout.item_null));

    }


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HomePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_post)
    void serviceClick() {


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
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadGoods("20", page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadGoods("20", page, pageSize);
        }
        return false;
    }


    @Override
    public void loadTag(List<CategoryVo> taglist) {

    }

    @Override
    public void loadError(String msg) {
        hideLoading();
    }

    @Override
    public void loadTodaysRecommend(EntityResult<HomeRecommend> result) {
        if (result.getCode() == 0) {
            //功能点
            topBanner.setData(result.getResult().getTopAdv());
            funcHolder.setData(result.getResult().getFuncList());
            middleBanner.setData(result.getResult().getMiddleAdv());
            headHolder.setData(result.getResult().getHotHead());
            for (int i = 0; i < result.getResult().getRecModuleSet().size(); i++) {
                if (i == 0) {
                    middle1.setData(result.getResult().getRecModuleSet().get(i));
                } else if (i == 1) {
                    middle2.setData(result.getResult().getRecModuleSet().get(i));
                } else if (i == 2) {
                    middle3.setData(result.getResult().getRecModuleSet().get(i));
                } else if (i == 3) {
                    middle4.setData(result.getResult().getRecModuleSet().get(i));
                } else {
                    middle5.setData(result.getResult().getRecModuleSet().get(i));
                }
            }
        }
        hideLoading();
    }

    @Override
    public void refresh(List<HomeGoodInfo> result) {
        isLoadMore(result);
        goods.clear();
        goods.addAll(result);
        goodHolder.setData(goods);
        hideLoading();
    }

    @Override
    public void loadMore(List<HomeGoodInfo> result) {
        isLoadMore(result);
        goods.addAll(result);
        goodHolder.setData(goods);
        hideLoading();
    }

    private void isLoadMore(List<HomeGoodInfo> result) {
        if (!ListUtils.listIsNullOrEmpty(result)) {
            if (result.size() < pageSize) {
                isRefresh = false;
            } else {
                isRefresh = true;
            }
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    /**
     * 返回顶部
     */
    @OnClick(R.id.totop_iv)
    void totopOnClick() {

        listView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                        listView.setSelection(0);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

    }

}
