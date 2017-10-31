package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.GoodInfo;
import com.colpencil.redwood.bean.Goodspec;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.Info.StoreDetail;
import com.colpencil.redwood.bean.Product;
import com.colpencil.redwood.bean.PromotionVo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.function.widgets.dialogs.PromoDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.GoodLeftPresenter;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.adapters.GoodTypeAdapter;
import com.colpencil.redwood.view.adapters.PromotionAdapter;
import com.colpencil.redwood.view.adapters.RecommendAdapter;
import com.colpencil.redwood.view.adapters.ServiceAdapter;
import com.colpencil.redwood.view.impl.IGoodLeftView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
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
        contentViewId = R.layout.fragment_good_left)
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
    @Bind(R.id.iv_head)
    SelectableRoundedImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_lv)
    ImageView ivLv;
    @Bind(R.id.tv_lv)
    TextView tvLv;
    @Bind(R.id.iv_store_type)
    ImageView ivStoreType;
    @Bind(R.id.tv_store_type)
    TextView tvStoreType;
    @Bind(R.id.tv_prize_count)
    TextView tvPrizeCount;
    @Bind(R.id.tv_fans_count)
    TextView tvFansCount;
    @Bind(R.id.unfocus_layout)
    LinearLayout unfocusLayout;
    @Bind(R.id.befocus_layout)
    LinearLayout befocusLayout;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.store_layout)
    LinearLayout storeLayout;
    private int store_id;
    //    @Bind(R.id.webview)
    //    PageTwoWebView webview;

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
    private Observable<RxBusMsg> rxObservable;
    private Subscriber rxSubcriber;
    private Map<String, String> chooseSpec = new HashMap<>();     //选中的商品
    private GoodInfo goodInfo;
    private StoreDetail storeDetail;
    private int type;

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
        rxObservable = RxBus.get().register("rxBusMsg",RxBusMsg.class);
        rxSubcriber = new Subscriber<RxBusMsg>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
               if(rxBusMsg.getType()==510){
                   unfocusLayout.setVisibility(View.GONE);
                   befocusLayout.setVisibility(View.VISIBLE);
               }else if(rxBusMsg.getType() == 511){
                   unfocusLayout.setVisibility(View.VISIBLE);
                   befocusLayout.setVisibility(View.GONE);
               }
            }
        };
        rxObservable.subscribe(rxSubcriber);
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
        //        WebSettings settings = webview.getSettings();
        //        settings.setJavaScriptEnabled(true);
        //        settings.setDomStorageEnabled(true);
        //        webview.setWebViewClient(new WebViewClient() {
        //            @Override
        //            public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //                view.loadUrl(url);
        //                return super.shouldOverrideUrlLoading(view, url);
        //            }
        //        });
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
        storeDetail = info.getStore_info();
        if(storeDetail!=null){
            if(storeDetail.getStore_id()!=null){
                store_id = storeDetail.getStore_id();
            }

            type = storeDetail.getStore_type();
        }
        if (storeDetail == null || store_id == 0) {
            storeLayout.setVisibility(View.GONE);
        } else {
            storeLayout.setVisibility(View.VISIBLE);
            tvName.setText(storeDetail.getStore_name());
            ImageLoaderUtils.loadImage(getActivity(), storeDetail.getFace(), ivHead);
            ImageLoaderUtils.loadImage(getActivity(), storeDetail.getMember_photo(), ivLv);
            tvLv.setText(storeDetail.getLv_name());
            ImageLoaderUtils.loadImage(getActivity(), storeDetail.getStore_type_path(), ivStoreType);
            tvStoreType.setText(storeDetail.getStore_type_name());
            tvAddress.setText(storeDetail.getStore_city());
            tvPrizeCount.setText(storeDetail.getPrize_count() + "");
            tvFansCount.setText(storeDetail.getStore_count() + "");
            if (storeDetail.getIsfocus() == 1) {
                unfocusLayout.setVisibility(View.VISIBLE);
                befocusLayout.setVisibility(View.GONE);
            } else {
                unfocusLayout.setVisibility(View.GONE);
                befocusLayout.setVisibility(View.VISIBLE);
            }
        }


    }

    @OnClick(R.id.store_layout)
    void toStore() {
        Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("store_id", store_id);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.unfocus_layout)
    void care() {
        //关注
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("fans_type", 5 + "");//关注
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            if (storeDetail.getMember_id() != null) {
                params.put("concern_id", storeDetail.getMember_id() + "");
                presenter.getCareReturn(params);
            }
        } else {
            showLogin();
        }

    }

    private void showLogin() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {
            presenter.loadGoodInfo(goodid + "");
        }
    }

    @OnClick(R.id.befocus_layout)
    void quitCare() {
        //取消关注
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fans_type", 6 + "");//关注
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        if (storeDetail.getMember_id() != null) {
            params.put("concern_id", storeDetail.getMember_id() + "");
            presenter.getUncareReturn(params);
        }
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
        //        if (result.getCode().equals("1")) {
        //            webview.loadUrl(result.getUrl());
        //        }
    }

    @Override
    public void loadRecommendError() {
        hideLoading();
        recommend_recycler.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg",rxObservable);
        RxBus.get().unregister(StringConfig.GOODSBUS,observable);
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
        tv_salenum.setText( "月销量："+info.getGoodsales());  //月销量
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void care(CareReturn result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
        if (storeDetail != null && result.getCode() == 0) {
            unfocusLayout.setVisibility(View.GONE);
            befocusLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void uncare(CareReturn result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
        if (storeDetail != null && result.getCode() == 0) {
            unfocusLayout.setVisibility(View.VISIBLE);
            befocusLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail(String msg) {
        ToastTools.showShort(getActivity(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
