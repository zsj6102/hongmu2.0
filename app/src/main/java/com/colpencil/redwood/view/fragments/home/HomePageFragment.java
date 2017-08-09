package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.holder.HolderFactory;
import com.colpencil.redwood.holder.home.FuncViewHolder;
import com.colpencil.redwood.holder.home.GoodHeadViewHolder;
import com.colpencil.redwood.holder.home.GoodViewHolder;
import com.colpencil.redwood.holder.home.MiddleItemViewHolder;
import com.colpencil.redwood.holder.home.TopBannerViewHolder;
import com.colpencil.redwood.holder.home.ViewpagerGridViewHolder;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.HomePresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.NewShopingCartActivity;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.ShoppingCartActivity;
import com.colpencil.redwood.view.activity.home.CategoryActivity;
import com.colpencil.redwood.view.activity.home.CodeActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.home.SearchActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IHomePageView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


/**
 * @author 陈 宝
 * @Description:首页
 * @Email 1041121352@qq.com
 * @date 2016/9/22
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_homepage
)
public class HomePageFragment extends ColpencilFragment implements IHomePageView, BGARefreshLayoutDelegate {

    @Bind(R.id.myhead)
    RelativeLayout rl_header;
    @Bind(R.id.search_header_hint)
    TextView input_hint;
    @Bind(R.id.tab_layout_header)
    LinearLayout tab_header;
    @Bind(R.id.ll_tablayout)
    LinearLayout ll_tablayout;
    @Bind(R.id.home_listview)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.search_header_code)
    ImageView iv_code;

    private HomePresenter presenter;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
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
        initBus();
    }

    private void initHeader() {
        rl_header.setBackgroundColor(getResources().getColor(R.color.main_brown));
        input_hint.setText("搜你想搜的");
        iv_code.setVisibility(View.VISIBLE);
        tab_header.setBackgroundColor(getResources().getColor(R.color.color_fff5f4));
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
    }
    @Override
    public void loadData() {
        presenter.loadRecommend();
        presenter.loadGoods("20", page, pageSize);
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            presenter.loadMyTag();
        } else {
            presenter.loadTag(2);
        }
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
            public void onNext(RxBusMsg tagMsg) {
                if (tagMsg.getType() == 58 || tagMsg.getType() == 4 || tagMsg.getType() == 63 || tagMsg.getType() == 200) {
                    presenter.loadMyTag();
                } else if (tagMsg.getType() == 53) {
                    presenter.loadTag(2);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HomePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }


    @OnClick(R.id.search_header_car)
    void onClick() {
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(getActivity(), NewShopingCartActivity.class);
            startActivity(intent);
        } else {
            final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra(StringConfig.REQUEST_CODE, 100);
                    startActivityForResult(intent, Constants.REQUEST_LOGIN);
                    dialog.dismiss();
                }

                @Override
                public void cancleOnClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @OnClick(R.id.search_header_ll)
    void searchClick() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_iv)
    void cateOnClick() {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.search_header_code)
    void codeClick() {
        Intent intent = new Intent(getActivity(), CodeActivity.class);
        startActivity(intent);
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
        removeView();
        if (!ListUtils.listIsNullOrEmpty(taglist)) {
            for (int i = 0; i < taglist.size(); i++) {
                CategoryVo vo = taglist.get(i);
                TextView tv = new TextView(getActivity());
                LinearLayout.LayoutParams lp =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                tv.setLayoutParams(lp);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding((int) (getResources().getDimension(R.dimen.padding_10dp)), 0,
                        (int) (getResources().getDimension(R.dimen.padding_10dp)), 0);
                tv.setBackgroundColor(getResources().getColor(R.color.transparent));
                tv.setTextSize(14);
                tv.setTextColor(getResources().getColor(R.color.text_color_first));
                tv.setText(vo.getCat_name());
                tv.setTag(i);
                tv.setOnClickListener(listener);
                ll_tablayout.addView(tv);
            }
        }
        hideLoading();
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
    }

    @Override
    public void refresh(List<HomeGoodInfo> result) {
        isLoadMore(result);
        goods.clear();
        goods.addAll(result);
        goodHolder.setData(goods);
    }

    @Override
    public void loadMore(List<HomeGoodInfo> result) {
        isLoadMore(result);
        goods.addAll(result);
        goodHolder.setData(goods);
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

    private void removeView() {
        int count = ll_tablayout.getChildCount();
        while (count > 1) {
            ll_tablayout.removeViewAt(count - 1);
            count = ll_tablayout.getChildCount();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            RxBusMsg bus = new RxBusMsg();
            bus.setType(56);
            bus.setPosition(position + 1);
            RxBus.get().post("rxBusMsg", bus);
        }
    };

    /**
     * 返回顶部
     */
    @OnClick(R.id.totop_iv)
    void totopOnClick() {
        listView.setSelection(0);
    }

}
