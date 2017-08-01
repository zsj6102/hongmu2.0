package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.GoodInfo;
import com.colpencil.redwood.bean.Goodspec;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.Product;
import com.colpencil.redwood.bean.PromotionVo;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.PromoDialog;
import com.colpencil.redwood.function.widgets.scroll.PageTwoWebView;
import com.colpencil.redwood.present.home.GoodLeftPresenter;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.adapters.GoodTypeAdapter;
import com.colpencil.redwood.view.adapters.PromotionAdapter;
import com.colpencil.redwood.view.adapters.RecommendAdapter;
import com.colpencil.redwood.view.adapters.ServiceAdapter;
import com.colpencil.redwood.view.impl.IGoodLeftView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:商品详情的Fragment
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_good_left
)
public class GoodLeftFragment extends ColpencilFragment implements IGoodLeftView {

    @Bind(R.id.good_banner)
    Banner banner;
    @Bind(R.id.good_left_goodname)
    TextView tv_goodname;
    @Bind(R.id.good_left_price)
    TextView tv_price;  //促销价
    @Bind(R.id.good_left_list)
    TextView tv_list;   //原价
    @Bind(R.id.good_introduce_recycler)
    RecyclerView reserver;//服务
    @Bind(R.id.good_recommend_recycler)
    RecyclerView recommend_recycler;    //商品推荐
    @Bind(R.id.good_left_salenum)
    TextView tv_salenum;
    @Bind(R.id.promotion_listview)
    MosaicListView plistView;   //促销方式
    @Bind(R.id.tv_good_num)
    TextView tv_num;
    @Bind(R.id.good_type)
    RecyclerView re_type;
    @Bind(R.id.webview)
    PageTwoWebView webview;

    private int goodid;     //商品id
    private GoodLeftPresenter presenter;
    private ServiceAdapter sAdapter;    //服务的适配器
    private List<String> servers = new ArrayList<>();   //服务
    private PromotionAdapter pAdapter;  //促销的适配器
    private List<PromotionVo> promots = new ArrayList<>();  //促销
    private RecommendAdapter rAdapter;  //推荐商品的适配器
    private List<HomeGoodInfo> goods = new ArrayList<>();   //商品
    private GoodTypeAdapter gtAdapter;  //商品规格的适配器
    private List<Goodspec> gspec = new ArrayList<>();   //商品规格
    private PromoDialog dialog;
    private Observable<GoodBusMsg> observable;
    private Subscriber subscriber;
    private Map<String, String> chooseSpec = new HashMap<>();     //选中的商品
    private GoodInfo goodInfo;

    public static GoodLeftFragment getInstance(int goodid) {
        Bundle bundle = new Bundle();
        bundle.putInt("goodsId", goodid);
        GoodLeftFragment fragment = new GoodLeftFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        goodid = getArguments().getInt("goodsId");
        initHeader();
        initBus();
        initWebView();
    }

    private void initHeader() {
        sAdapter = new ServiceAdapter(getActivity(), servers);
        LinearLayoutManager smanager = new LinearLayoutManager(getActivity());
        smanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        reserver.setLayoutManager(smanager);
        reserver.setAdapter(sAdapter);

        pAdapter = new PromotionAdapter(getActivity(), promots, R.layout.item_good_promotion);
        plistView.setAdapter(pAdapter);

        LinearLayoutManager rmanager = new LinearLayoutManager(getActivity());
        rmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recommend_recycler.setHasFixedSize(true);
        recommend_recycler.setLayoutManager(rmanager);
        rAdapter = new RecommendAdapter(getActivity(), goods);
        recommend_recycler.setAdapter(rAdapter);

        LinearLayoutManager gtmanager = new LinearLayoutManager(getActivity());
        gtmanager.setOrientation(LinearLayoutManager.VERTICAL);
        re_type.setLayoutManager(gtmanager);
        gtAdapter = new GoodTypeAdapter(getActivity(), gspec);
        re_type.setAdapter(gtAdapter);
        plistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dialog == null) {
                    dialog = new PromoDialog(getActivity());
                }
                dialog.setTitle("促销");
                dialog.setPromots(promots);
                dialog.show();
            }
        });
    }

//    private void loadData() {
//
//    }
@Override
public void loadData() {
    presenter.loadGoodInfo(goodid + "");
            presenter.loadRecommend(8, 1, 3);
            presenter.loadGoodDetail(goodid);
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
                if ("Reload".equals(msg.getType())) {   //刷新界面数据
                    Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                    intent.putExtra("goodsId", msg.getGoodsId());
                    startActivity(intent);
                    getActivity().finish();
                } else if (StringConfig.CHOOSENORM.equals(msg.getType())) {     //选中规格
                    chooseSpec.put(msg.getSpecification(), msg.getNorm());
                    updatePrice();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void initWebView() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new GoodLeftPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadGoodInfo(GoodInfo info) {
        hideLoading();
        goodInfo = info;
        initBanner(info.getImglist());
        initGood(info);
    }

    @Override
    public void loadGoodInfoError() {
        hideLoading();
    }

    @Override
    public void loadRecommend(List<HomeGoodInfo> goodlist) {
        hideLoading();
        goods.clear();
        goods.addAll(goodlist);
        rAdapter.notifyDataSetChanged();
        recommend_recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadDetail(AnnounceResult result) {
        if (result.getCode().equals("1")) {
            webview.loadUrl(result.getUrl());
        }
    }

    @Override
    public void loadRecommendError() {
        hideLoading();
        recommend_recycler.setVisibility(View.GONE);
    }

    /**
     * 设置banner
     *
     * @param imgs
     */
    private void initBanner(final List<String> imgs) {
        if (!ListUtils.listIsNullOrEmpty(imgs)) {
            banner.isAutoPlay(true); //设置自动滚动
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//指示器模式
            banner.setIndicatorGravity(BannerConfig.CENTER);//指示器位置
            banner.setVisibility(View.VISIBLE);
            banner.setImageLoader(new MyImageLoader());
            banner.setImages(imgs);
            banner.start();
        } else {
            banner.setVisibility(View.GONE);
        }
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), GalleyActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) imgs);
                intent.putExtra("position", position - 1);
                startActivity(intent);
            }
        });
    }

    private void initGood(GoodInfo info) {
        tv_goodname.setText(info.getGoodsname());
        tv_price.setText("￥" + info.getSaleprice()); //促销价
        tv_list.setText("￥" + info.getCostprice()); //原价
        tv_list.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);    //添加下划线
        tv_salenum.setText(String.format(getString(info.getGoodsales())));  //月销量
        sAdapter.notifys(info.getGoodservice());
        gtAdapter.notifys(info.getGoodspec());
        pAdapter.notifys(info.getPromotions());
    }

    private void updatePrice() {
        if (ListUtils.listIsNullOrEmpty(goodInfo.getGoodspec())) {
            tv_price.setText("￥" + goodInfo.getSaleprice()); //促销价
        } else {
            int size = chooseSpec.size();
            if (!chooseSpec.isEmpty()) {
                for (Product product : goodInfo.getProductList()) {
                    String specs = product.getSpecs();
                    int count = 0;
                    for (String str : chooseSpec.values()) {
                        if (specs.contains(str)) {
                            count++;
                        }
                    }
                    if (size == count) {
                        tv_price.setText("￥" + product.getPrice()); //促销价
                        return;
                    } else {
                        tv_price.setText("￥" + goodInfo.getSaleprice()); //促销价
                    }
                }
            } else {
                tv_price.setText("￥" + goodInfo.getSaleprice()); //促销价
            }
        }
    }

    @OnClick(R.id.btn_reduce)
    void reduceOnClick() {
        int num = Integer.valueOf(tv_num.getText().toString());
        if (num <= 1) {
            tv_num.setClickable(false);
        } else {
            tv_num.setClickable(true);
            num--;
            tv_num.setText(num + "");
            GoodBusMsg busMsg = new GoodBusMsg();
            busMsg.setGoodsNum(num);
            busMsg.setType(StringConfig.CHANGENUM);

            RxBus.get().post(StringConfig.GOODSBUS, busMsg);
        }
    }

    @OnClick(R.id.btn_increase)
    void increaseOnClick() {
        int num = Integer.valueOf(tv_num.getText().toString());
        num++;
        tv_num.setText(num + "");
        GoodBusMsg busMsg = new GoodBusMsg();
        busMsg.setGoodsNum(num);
        busMsg.setType(StringConfig.CHANGENUM);
        RxBus.get().post(StringConfig.GOODSBUS, busMsg);
    }

//    class HeaderViewHolder {
//
//        @Bind(R.id.good_banner)
//        Banner banner;
//        @Bind(R.id.good_left_goodname)
//        TextView tv_goodname;
//        @Bind(R.id.good_left_price)
//        TextView tv_price;  //促销价
//        @Bind(R.id.good_left_list)
//        TextView tv_list;   //原价
//        @Bind(R.id.good_introduce_recycler)
//        RecyclerView reserver;//服务
//        @Bind(R.id.good_recommend_recycler)
//        RecyclerView recommend_recycler;    //商品推荐
//        @Bind(R.id.good_left_salenum)
//        TextView tv_salenum;
//        @Bind(R.id.promotion_listview)
//        MosaicListView plistView;   //促销方式
//        @Bind(R.id.tv_good_num)
//        TextView tv_num;
//        @Bind(R.id.good_type)
//        RecyclerView re_type;
//
//        public HeaderViewHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//
//        public void unbind() {
//            ButterKnife.unbind(this);
//        }
//
//        @OnClick(R.id.btn_reduce)
//        void reduceOnClick() {
//            int num = Integer.valueOf(tv_num.getText().toString());
//            if (num <= 0) {
//                return;
//            }
//            num--;
//            tv_num.setText(num + "");
//            GoodBusMsg busMsg = new GoodBusMsg();
//            busMsg.setGoodsNum(num);
//            busMsg.setType(StringConfig.CHANGENUM);
//            RxBus.get().post(StringConfig.GOODSBUS, busMsg);
//        }
//
//        @OnClick(R.id.btn_increase)
//        void increaseOnClick() {
//            int num = Integer.valueOf(tv_num.getText().toString());
//            num++;
//            tv_num.setText(num + "");
//            GoodBusMsg busMsg = new GoodBusMsg();
//            busMsg.setGoodsNum(num);
//            busMsg.setType(StringConfig.CHANGENUM);
//            RxBus.get().post(StringConfig.GOODSBUS, busMsg);
//        }
//    }
}
