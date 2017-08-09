package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BidderInfoVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.WeekPersonList;
import com.colpencil.redwood.bean.result.WeekShootDetailResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.function.widgets.dialogs.OfferDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.ProductdetailsPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.PaymentActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.adapters.ProductDetailsGVBiddingAdapter;
import com.colpencil.redwood.view.adapters.ProductDetailsGVImgAdapter;
import com.colpencil.redwood.view.adapters.ProductDetailsLVBiddingAdapter;
import com.colpencil.redwood.view.impl.IProductdetailsView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.MosaicGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.MosaicListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/8 17 50
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_productdetails
)
public class ProductdetailsActivity extends ColpencilActivity implements IProductdetailsView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.tv_current_price)
    TextView current_price;
    @Bind(R.id.current_price_tv)
    TextView tv_current;
    @Bind(R.id.tv_offer)
    Button btn_offer;
    @Bind(R.id.listview_productdetails)
    BaseListView listView;
    @Bind(R.id.base_header_layout)
    RelativeLayout base_header_layout;

    private HeaderViewHolder headerholder;//头部的ViewHolder
    private BottomViewHolder bottomholder;//底部的ViewHolder
    private ProductDetailsLVBiddingAdapter lvBiddingAdapter;//前三名竞拍人的适配器
    private ProductdetailsPresenter presenter;
    private OfferDialog offerDialog;//出价框
    private int goodsId;
    private List<BidderInfoVo> bidders = new ArrayList<>();
    private String price;
    private WeekShootDetailResult mResult;
    private Timer timer = new Timer();
    private long countTime;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        goodsId = getIntent().getIntExtra("goodsId", 0);
        tv_main_title.setText("周拍详情");

        //周拍结果
        base_header_layout.setVisibility(View.VISIBLE);
        base_header_layout.setBackgroundColor(getResources().getColor(R.color.main_background));

        //头布局
        View headerView = View.inflate(this, R.layout.header_productdetails, null);
        headerholder = new HeaderViewHolder(headerView);
        //底布局
        View bottomView = View.inflate(this, R.layout.bottom_productdetails, null);
        bottomholder = new BottomViewHolder(bottomView);
        listView.addHeaderView(headerView);
        listView.addHeaderView(bottomView);
        listView.setAdapter(new NullAdapter(ProductdetailsActivity.this, new ArrayList<String>(), R.layout.item_null));
        initAdapter();
        initData();

        handler.postDelayed(runnable, 10000);
    }

    private void initAdapter() {
        lvBiddingAdapter = new ProductDetailsLVBiddingAdapter(this, bidders, R.layout.item_productdetailsbiddingcontent);
        bottomholder.listView.setAdapter(lvBiddingAdapter);
    }

    private void initData() {
        //周拍结果
        bottomholder.pd_buy.setVisibility(View.GONE);
        showLoading("");
        presenter.loadGoodInfo(goodsId);
        presenter.loadPersons(goodsId);
        presenter.loadBidder(goodsId);
        presenter.loadUrl(goodsId);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ProductdetailsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    /**
     * 我要出价
     */
    @OnClick(R.id.tv_offer)
    void offerOnClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            if (mResult.getStatus() == 1) {     //竞拍中
                if (offerDialog == null) {
                    offerDialog = new OfferDialog(this);
                }
                offerDialog.setTips(mResult.getMark_up());
                offerDialog.setListener(new OfferDialog.DialogClickListener() {
                    @Override
                    public void confirmOnClick(String price) {
                        ProductdetailsActivity.this.price = price;
                        if (Float.valueOf(price) < mResult.getMark_up() + mResult.getPrice()) {
                            ToastTools.showShort(ProductdetailsActivity.this, "加价幅度不能低于" + mResult.getMark_up() + "");
                        } else {
                            showLoading("正在提交中...");
                            presenter.submitBid(price, goodsId);
                            offerDialog.dismiss();
                        }
                    }

                    @Override
                    public void cancelOnClick() {
                        offerDialog.dismiss();
                    }
                });
                offerDialog.show();
            } else {       //已结束
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra("goFrom", "MyWeekShootFragment");
                intent.putExtra("product_id", mResult.getProduct_id());
                intent.putExtra("goods_id", mResult.getGoods_id());
                intent.putExtra("key", "订单确认");
                startActivity(intent);
                finish();
            }
        } else {
            final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    Intent intent = new Intent(ProductdetailsActivity.this, LoginActivity.class);
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

    /**
     * @deprecated 加载头部内容
     */
    @Override
    public void loadHeaderData(WeekShootDetailResult result) {
        hideLoading();
        if (result.getCode() == 1) {
            initGood(result);
            initState(result);
            initTimes(result);
        }
    }

    /**
     * 设置商品数据
     *
     * @param result
     */
    private void initGood(WeekShootDetailResult result) {
        mResult = result;
        headerholder.tv_goodname.setText("【" + result.getType_name() + "】 " + result.getGoods_name());
        BigDecimal bd = new BigDecimal(Double.toString(result.getPrice()));
        current_price.setText("￥" + bd.toPlainString());
        List<String> datas = new ArrayList<>();
        if (result.getImg().size() > 9) {
            for (int i = 0; i < 9; i++) {
                datas.add(result.getImg().get(i));
            }
        } else {
            datas.addAll(result.getImg());
        }
        List<String> imgs = new ArrayList<>();
        imgs.addAll(result.getImg());
        headerholder.gridView.setAdapter(new ProductDetailsGVImgAdapter(this, datas, R.layout.item_productdetailsimg, imgs));
    }

    /**
     * 设置时间
     *
     * @param result
     */
    private void initTimes(WeekShootDetailResult result) {
        if (result.getStatus() == 0) {        //未开始
            if (TimeUtil.getDay(result.getSystem_time(), result.getBegin_time()) > 0) {
                headerholder.tv_day.setText(TimeUtil.getDay(result.getSystem_time(), result.getBegin_time()) + "天");
                headerholder.tv_day.setVisibility(View.VISIBLE);
            } else {
                headerholder.tv_day.setText(0 + "天");
                headerholder.tv_day.setVisibility(View.GONE);
            }
            headerholder.weekShoot_tv.setText("距离开始：");
            countTime = result.getBegin_time() - result.getSystem_time();
            timer.schedule(task, 1000, 1000);
        } else if (result.getStatus() == 1) {       //竞拍中
            if (TimeUtil.getDay(result.getSystem_time(), result.getEnd_time()) > 0) {
                headerholder.tv_day.setText(TimeUtil.getDay(result.getSystem_time(), result.getEnd_time()) + "天");
                headerholder.tv_day.setVisibility(View.VISIBLE);
            } else {
                headerholder.tv_day.setText(0 + "天");
                headerholder.tv_day.setVisibility(View.GONE);
            }
            headerholder.weekShoot_tv.setText("距离结束：");
            countTime = result.getEnd_time() - result.getSystem_time();
            timer.schedule(task, 1000, 1000);
        } else {    //已结束
            headerholder.weekShoot_tv.setText("已结束：");
            headerholder.tv_day.setText(0 + "天");
            headerholder.tv_day.setVisibility(View.GONE);
        }
    }

    private void initState(WeekShootDetailResult result) {
        if (result.getStatus() == 0) {      //未开始
            current_price.setVisibility(View.GONE);
            tv_current.setText("周拍未开始");
            btn_offer.setText("未开始");
            btn_offer.setClickable(false);
            btn_offer.setBackgroundResource(R.drawable.gray_solid_shape);
            lvBiddingAdapter.setState(true);
        } else if (result.getStatus() == 1) {   //竞拍中
            current_price.setVisibility(View.VISIBLE);
            tv_current.setText("当前拍价：");
            btn_offer.setText("我要出价");
            btn_offer.setClickable(true);
            btn_offer.setBackgroundResource(R.drawable.red_solid_shape);
            lvBiddingAdapter.setState(false);
        } else {    //已结束
            current_price.setVisibility(View.GONE);
            lvBiddingAdapter.setState(true);
            if (result.getWinner() == SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")) {
                tv_current.setText("我已拍得");
                if (result.getBuy_status() == 0) {
                    btn_offer.setText("去付款");
                    btn_offer.setClickable(true);
                    btn_offer.setBackgroundResource(R.drawable.red_solid_shape);
                } else {
                    btn_offer.setText("已结束");
                    btn_offer.setClickable(false);
                    btn_offer.setBackgroundResource(R.drawable.gray_solid_shape);
                }
            } else {
                tv_current.setText("竞拍结束");
                btn_offer.setText("已结束");
                btn_offer.setClickable(false);
                btn_offer.setBackgroundResource(R.drawable.gray_solid_shape);
            }
        }
    }

    /**
     * 竞拍信息设置
     */
    @Override
    public void webUrl(EntityResult<String> result) {
        WebSettings settings = headerholder.webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        headerholder.webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        if (result.getCode() == 1) {
            headerholder.webView.loadUrl(result.getUrl());
        }
    }

    @Override
    public void loadPersonList(List<WeekPersonList> result) {
        hideLoading();
        bottomholder.tv_count.setText("竞拍（" + result.size() + "人）：");
        bottomholder.gridView.setAdapter(new ProductDetailsGVBiddingAdapter(this, result, R.layout.item_productdetailsbiddingimg));
    }

    @Override
    public void loadBidders(List<BidderInfoVo> lists) {
        hideLoading();
        bidders.clear();
        bidders.addAll(lists);
        lvBiddingAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            presenter.loadBidder(goodsId);
            presenter.loadGoodInfo(goodsId);
            presenter.loadPersons(goodsId);
            RxBusMsg msg = new RxBusMsg();
            msg.setType(65);
            RxBus.get().post("rxBusMsg", msg);
            final CommonDialog dialog = new CommonDialog(this, "出价成功", "好的", "");
            dialog.setCancelVisiable(View.GONE);
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
        } else if (result.getCode() == 3) {     //未登录
            ToastTools.showShort(this, result.getMsg());
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        headerholder.unbind();
        bottomholder.unbind();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        timer.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {
            presenter.submitBid(price, goodsId);
        }
    }

    class HeaderViewHolder {

        @Bind(R.id.gridview_productdetails_img)
        MosaicGridView gridView;
        @Bind(R.id.productdetails_time)
        TextView countdown;
        @Bind(R.id.header_product_goodname)
        TextView tv_goodname;
        @Bind(R.id.weekShoot_day)
        TextView tv_day;
        @Bind(R.id.weekShoot_tv)
        TextView weekShoot_tv;
        @Bind(R.id.webview)
        WebView webView;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }
    }

    class BottomViewHolder {

        @Bind(R.id.gridview_bidding)
        MosaicGridView gridView;
        @Bind(R.id.tv_pdBiddingCount)
        TextView tv_count;
        @Bind(R.id.listview_bidding)
        MosaicListView listView;
        @Bind(R.id.pd_buy)
        LinearLayout pd_buy;

        public BottomViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }
    }

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            presenter.loadGoodInfo(goodsId);
            presenter.loadPersons(goodsId);
            presenter.loadBidder(goodsId);
            handler.postDelayed(this, 10000);
        }
    };

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    countTime -= 1000;
                    if (countTime > 0) {
                        headerholder.countdown.setText(TimeUtil.getHourse(countTime) + ":" + TimeUtil.getMinute(countTime) + ":" + TimeUtil.getSecond(countTime));
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        presenter.loadGoodInfo(goodsId);
                        presenter.loadPersons(goodsId);
                        presenter.loadBidder(goodsId);
                        handler.removeCallbacks(runnable);
                        timer.cancel();
                    }
                }
            });
        }
    };
}
