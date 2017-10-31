package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.GoodInfo;
import com.colpencil.redwood.bean.Goodspec;
import com.colpencil.redwood.bean.Product;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.OrderPayInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.GoodDetailPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;

import com.colpencil.redwood.view.activity.ShoppingCartActivitys.NewShopingCartActivity;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.CommodityManageActivity;
import com.colpencil.redwood.view.fragments.home.GoodLeftFragment;
import com.colpencil.redwood.view.fragments.home.GoodMiddleFragment;
import com.colpencil.redwood.view.fragments.home.GoodRightFragment;
import com.colpencil.redwood.view.impl.IGoodDetailView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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
 * @Description:商品详情的Activity
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_good_detail)
public class GoodDetailActivity extends ColpencilActivity implements IGoodDetailView {

    @Bind(R.id.good_detail_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.good_detail_vp)
    NoScrollViewPager vp;
    @Bind(R.id.iv_collection)
    ImageView iv_collection;

    private MyPagerAdapter adapter;
    public int goodid;
    private GoodDetailPresenter presenter;

    private Observable<GoodBusMsg> observable;
    private Subscriber subscriber;
    private int productId;      //规格id
    private int goodnum = 1;
    private int store = 0;
    private String shareUrl = "";
    private PopupWindow window;
    private View view;
    private ShareAction action;
    private UMImage image;
    private Map<String, String> spec = new HashMap<>();
    private List<Product> productList;
    private List<Goodspec> goodspecs = new ArrayList<>();
    private double type;
    private GoodInfo mResult;
    private Integer storeid;
    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        this.view = view;
        goodid = getIntent().getExtras().getInt("goodsId");
        initvp();
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv_good)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv_detail)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv_evaluation)));
        tabLayout.setupWithViewPager(vp);
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
                if (msg.getType().equals(StringConfig.CHANGENUM)) {     //修改商品数量
                    goodnum = msg.getGoodsNum();
                } else if (msg.getType().equals(StringConfig.CHOOSENORM)) {     //选择规格
                    spec.put(msg.getSpecification(), msg.getNorm());
                }
            }
        };
        observable.subscribe(subscriber);
        action = new ShareAction(this);
    }

    private void initvp() {
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GoodLeftFragment.getInstance(goodid), "商品");
        adapter.addFragment(GoodMiddleFragment.getInstance(goodid), getString(R.string.tv_detail));
        adapter.addFragment(GoodRightFragment.getInstance(goodid), getString(R.string.tv_evaluation));
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);
        showLoading("");
        presenter.loadState(goodid);
        presenter.browerGood(goodid);
        presenter.loadShareUrl(goodid);
        presenter.loadGoodInfo(goodid);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new GoodDetailPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void addtocarsuccess(EntityResult<String> result) {
        if (result.getCode() == 0) {
            showDialog("加入购物车成功", "知道了", "", View.GONE);
        } else if (result.getCode() == 3) {
            type = 1;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        } else {
            ToastTools.showShort(this, result.getMsg());
        }
    }

    /**
     * 收藏
     */
    @OnClick(R.id.iv_collection)
    void collectionClick() {
        showLoading("");
        presenter.keepGood(goodid);
    }

    @OnClick(R.id.iv_car)
    void toCarClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(this, NewShopingCartActivity.class);
            startActivity(intent);
        } else {
            type = 3;
            showDialog();
        }
    }

    @Override
    public void loadNewOrder(OrderPayInfo result) {

        if(result.getCode() == 0){
            Intent intent = new Intent();
            intent.setClass(this, OrderActivity.class);
            intent.putExtra("key", "订单确定");
            intent.putExtra("goFrom", "GoodDetailActivity");
            intent.putExtra("product_id", productId);    //int类型
            intent.putExtra("goods_id", goodid);     //int类型
            intent.putExtra("num", goodnum);    //int类型
            startActivity(intent);
        }else{
            ToastTools.showShort(this,result.getMessage());
        }

    }

    @OnClick(R.id.iv_share)
    void shareOnClick() {
        if (!TextUtils.isEmpty(shareUrl)) {
            showPopWindow(view);
        } else {
            type = 2;
            showDialog();
        }
    }

    @OnClick(R.id.iv_service)
    void serviceClick() {
        if(mResult!=null){
            storeid = mResult.getStore_info().getStore_id();
            if(storeid!=null){
                if(storeid==0){
                    Intent intent = new Intent();
                    intent.setClass(this, MyWebViewActivity.class);
                    intent.putExtra("webviewurl", UrlConfig.SERVICE_URL);
                    intent.putExtra("type", "server");
                    startActivity(intent);
                }else{
                    if(SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)){
                        Intent intent = new Intent();
                        intent.setClass(this, CommodityManageActivity.class);
                        intent.putExtra("type","11");
                        intent.putExtra("storeid",storeid);
                        intent.putExtra("goodid",goodid);
                        startActivity(intent);
                    }else{
                        type = 6;
                        showDialog();
                    }

                }
            }
        }

    }

    @OnClick(R.id.iv_home)
    void toHome() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(3);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 检查商品收藏状态
     *
     * @param result
     */
    @Override
    public void checkState(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            if (result.getFavorite() == 1) {
                iv_collection.setImageResource(R.mipmap.favorited_icon);
            } else {
                iv_collection.setImageResource(R.mipmap.detail_collection_icon);
            }
        } else {
            iv_collection.setImageResource(R.mipmap.detail_collection_icon);
        }
    }

    /**
     * 收藏商品
     *
     * @param result
     */
    @Override
    public void keepGoodResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            iv_collection.setImageResource(R.mipmap.favorited_icon);
        } else {
            if (result.getCode() == 3) {    //未登录
                type = 0;
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                startActivityForResult(intent, Constants.REQUEST_LOGIN);
                ToastTools.showShort(this, result.getMsg());
            }
            iv_collection.setImageResource(R.mipmap.detail_collection_icon);
        }
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(4);
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }

    @Override
    public void shareResult(CommonResult result) {
        if ("1".equals(result.getCode())) {
            shareUrl = result.getUrl();
        }
    }

    @Override
    public void loadGoods(GoodInfo result) {
        productList = result.getProductList();
        mResult = result;
        goodspecs.clear();
        if (ListUtils.listIsNullOrEmpty(result.getGoodspec())) {
            if (!ListUtils.listIsNullOrEmpty(result.getProductList())) {
                productId = productList.get(0).getProduct_id();
                store = productList.get(0).getStore();
            }
        } else {
            goodspecs.addAll(result.getGoodspec());
        }
    }

    private int operate() {
        if (ListUtils.listIsNullOrEmpty(goodspecs)) {
            return productId;
        }
        int size = goodspecs.size();
        if (!spec.isEmpty()) {
            for (Product product : productList) {
                String specs = product.getSpecs();
                int count = 0;
                for (String str : spec.values()) {
                    if (specs.contains(str)) {
                        count++;
                    }
                }
                if (size == count) {
                    store = product.getStore();
                    productId = product.getProduct_id();
                    return productId;
                }
            }
            return -1;
        } else {
            return productId;
        }
    }

    private boolean isSelectAll() {
        int mSize = 0;
        for (String str : spec.values()) {
            if (!TextUtils.isEmpty(str) && !str.equals("  ")) {
                mSize++;
            }
        }
        if (mSize == goodspecs.size()) {
            return true;
        } else {
            return false;
        }
    }

    @OnClick(R.id.btn_addtocar)
    void addCar() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            if (goodnum == 0) {
                showDialog("最少需要购买一件商品", "知道了", "", View.GONE);
                return;
            } else if (!isSelectAll()) {
                showDialog("请选择完整的尺寸", "知道了", "", View.GONE);
                return;
            } else if (operate() == 0) {
                showDialog("来晚啦，没货啦！", "知道了", "", View.GONE);
                return;
            } else if (store <= 0) {
                showDialog("来晚啦，没货啦！", "知道了", "", View.GONE);
                return;
            } else if (store < goodnum) {
                showDialog("来晚啦，没货啦！", "知道了", "", View.GONE);
                return;
            } else {
                presenter.addtoCar(goodid, productId, goodnum);
            }
        } else {
            type = 3;
            showDialog();
        }
    }

    /**
     * 立即购买
     */
    @OnClick(R.id.buy_right)
    void buyRightNow() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            if (goodnum == 0) {
                showDialog("最少需要购买一件商品", "知道了", "", View.GONE);
                return;
            } else if (!isSelectAll()) {
                showDialog("请选择完整的尺寸", "知道了", "", View.GONE);
                return;
            } else if (operate() == 0) {
                showDialog("来晚啦，没货啦！", "知道了", "", View.GONE);
                return;
            } else if (store <= 0) {
                showDialog("来晚啦，没货啦！", "知道了", "", View.GONE);
                return;
            } else if (store < goodnum) {
                showDialog("来晚啦，没货啦！", "知道了", "", View.GONE);
                return;
            } else {
                Map<String, String> params = new HashMap<>();
                params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                params.put("goods_id",goodid+"");
                params.put("product_id",productId+"");
                params.put("num",goodnum+"");
                presenter.getDirectOrder(params);
            }
        } else {
            type = 3;
            showDialog();
        }
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(GoodDetailActivity.this, LoginActivity.class);
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

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    private void showPopWindow(View view) {
        if (window == null) {
            window = new PopupWindow(initPopWindow(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(view);
    }

    private View initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_share, null);
        view.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        view.findViewById(R.id.popwindow_null).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        view.findViewById(R.id.ll_share_qq).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QQ);
            }
        });
        view.findViewById(R.id.ll_share_weibo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.SINA);
            }
        });
        view.findViewById(R.id.ll_share_wechat).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN);
            }
        });
        view.findViewById(R.id.ll_share_circle).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        return view;
    }

    private void dismissPop() {
        if (window != null) {
            window.dismiss();
        }
    }

    private void share(SHARE_MEDIA platform) {
        if (mResult != null) {
            if (mResult.getImglist() != null) {
                image = new UMImage(GoodDetailActivity.this,
                        mResult.getImglist().get(0));
            } else {
                image = new UMImage(GoodDetailActivity.this,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
            }
            action.setPlatform(platform)
                    .withText(mResult.getGoodsname())
                    .withTargetUrl(shareUrl)
                    .withMedia(image)
                    .setCallback(umShareListener)
                    .share();
        } else {
            image = new UMImage(GoodDetailActivity.this,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
            action.setPlatform(platform)
                    .withText("商品分享")
                    .withTargetUrl(shareUrl)
                    .withMedia(image)
                    .setCallback(umShareListener)
                    .share();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            if (share_media == SHARE_MEDIA.QQ) {  //QQ
                presenter.addUpSharenum(3, "qq", goodid);
            } else if (share_media == SHARE_MEDIA.WEIXIN) {   //微信
                presenter.addUpSharenum(3, "friend", goodid);
            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {    //朋友圈
                presenter.addUpSharenum(3, "weixin", goodid);
            } else {    //新浪
                presenter.addUpSharenum(3, "xinlang", goodid);
            }
            dismissPop();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastTools.showShort(GoodDetailActivity.this, "分享失败");
            dismissPop();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            dismissPop();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            if (type == 0) {
                presenter.keepGood(goodid);
            } else if (type == 1) {
                presenter.addtoCar(goodid, productId, goodnum);
            } else if (type == 2) {
                presenter.loadShareUrl(goodid);
            }
        }
    }

    private void showDialog(String content, String left, String right, int mode) {
        final CommonDialog dialog = new CommonDialog(this, content, left, right);
        dialog.setCancelVisiable(mode);
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> stringList = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            stringList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }

}
