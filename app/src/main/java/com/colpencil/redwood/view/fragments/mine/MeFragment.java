package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.UserInfor;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.MeFragmentPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.ShoppingCartActivity;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.HelpActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.AfterSalesCenterActivity;
import com.colpencil.redwood.view.activity.mine.AllSpecialActivity;
import com.colpencil.redwood.view.activity.mine.BrowsingHistoryActivity;
import com.colpencil.redwood.view.activity.mine.BusinessActivity;
import com.colpencil.redwood.view.activity.mine.MineCycloActivity;
import com.colpencil.redwood.view.activity.mine.MyCollectionActivity;
import com.colpencil.redwood.view.activity.mine.MyCouponsActivity;
import com.colpencil.redwood.view.activity.mine.MyCustomActivity;
import com.colpencil.redwood.view.activity.mine.MyEvaluationActivity;
import com.colpencil.redwood.view.activity.mine.MyIntegralActivity;
import com.colpencil.redwood.view.activity.mine.MyMessageActivity;
import com.colpencil.redwood.view.activity.mine.MyNewsActivity;
import com.colpencil.redwood.view.activity.mine.MyWeekShootActivity;
import com.colpencil.redwood.view.activity.mine.OrderCenterActivity;
import com.colpencil.redwood.view.activity.mine.ReceiptAddressActivtiy;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.activity.mine.UserInformationActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.MeFragmentAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IMeFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TextStringUtils;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.MosaicGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

import static com.colpencil.redwood.R.id.businessCooperation;
import static com.colpencil.redwood.R.id.myEvaluation;
import static com.colpencil.redwood.R.id.tv_noLoginState;

/**
 * 描述：我的
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/6 11 00
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_me)
public class MeFragment extends ColpencilFragment implements IMeFragmentView, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.bga_refreshLayout_me)
    BGARefreshLayout bga_refreshLayout_me;

    @Bind(R.id.listView_me)
    BaseListView listView_me;

    private SelectableRoundedImageView iv_meHead;

    private TextView tv_cllectionCount;

    private TextView tv_integralBalance;

    private TextView tv_msgCount;

    private TextView tv_meUserName;

    private TextView tv_meUserID;

    private TextView tvCop;

    private LinearLayout ll_loginState;

    private LinearLayout ll_noLoginState;

    private MosaicGridView gridView_me;

    private MeFragmentPresenter presenter;

    private MeFragmentAdapter meAdapter;

    private List<HomeGoodInfo> mdata = new ArrayList<>();
    /**
     * 是否可以加载更多
     */
    private boolean isLoad;
    /**
     * 页数
     */
    private int pageNo = 1;
    /**
     * 分页长度
     */
    private int pageSize = 10;

    private Intent mIntent;

    private Observable<RxBusMsg> observable;
    private Observable<RxClickMsg> clickMsgObservable;
    private Subscriber subscriber;
    private Observable<RefreshMsg> refreshMsgObservable;
    private boolean loginState = false;//登录状态

    private CommonDialog servicedialog;


    @Override
    protected void initViews(View view) {
        bga_refreshLayout_me.setDelegate(this);
        bga_refreshLayout_me.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_refreshLayout_me.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.main_red));
        View headView = View.inflate(getActivity(), R.layout.fragment_mehead, null);
        iv_meHead = (SelectableRoundedImageView) headView.findViewById(R.id.iv_meHead);
        tv_cllectionCount = (TextView) headView.findViewById(R.id.tv_cllectionCount);
        tv_integralBalance = (TextView) headView.findViewById(R.id.tv_integralBalance);
        tv_msgCount = (TextView) headView.findViewById(R.id.tv_msgCount);
        tv_meUserName = (TextView) headView.findViewById(R.id.tv_meUserName);
        tv_meUserID = (TextView) headView.findViewById(R.id.tv_meUserID);
        ll_loginState = (LinearLayout) headView.findViewById(R.id.ll_loginState);
        ll_noLoginState = (LinearLayout) headView.findViewById(R.id.ll_noLoginState);
        tvCop = (TextView) headView.findViewById(R.id.tv_cop);
        headView.findViewById(R.id.custom_service).setOnClickListener(this);
        headView.findViewById(tv_noLoginState).setOnClickListener(this);
        headView.findViewById(R.id.ll_meEdit).setOnClickListener(this);
        headView.findViewById(R.id.ll_shoppingcar).setOnClickListener(this);
        headView.findViewById(R.id.main_order).setOnClickListener(this);
        headView.findViewById(R.id.waitpayment).setOnClickListener(this);
        headView.findViewById(R.id.waitreceive).setOnClickListener(this);
        headView.findViewById(R.id.waitevaluation).setOnClickListener(this);
        headView.findViewById(R.id.aftersale).setOnClickListener(this);
        headView.findViewById(R.id.waitdelivery).setOnClickListener(this);
        headView.findViewById(R.id.myWeekShoot).setOnClickListener(this);
        headView.findViewById(R.id.myCustom).setOnClickListener(this);
        headView.findViewById(R.id.businessCooperation).setOnClickListener(this);
        headView.findViewById(R.id.myCoupons).setOnClickListener(this);
        headView.findViewById(R.id.myEvaluation).setOnClickListener(this);
        headView.findViewById(R.id.adressManagement).setOnClickListener(this);
        headView.findViewById(R.id.myCollection).setOnClickListener(this);
        headView.findViewById(R.id.myCyclopedia).setOnClickListener(this);
        headView.findViewById(R.id.myNew).setOnClickListener(this);
        headView.findViewById(R.id.my_browsinghistory).setOnClickListener(this);
        headView.findViewById(R.id.myIntegra).setOnClickListener(this);
        headView.findViewById(R.id.myMessage).setOnClickListener(this);
        headView.findViewById(R.id.ll_aboutApp).setOnClickListener(this);
        headView.findViewById(R.id.ll_wallet).setOnClickListener(this);
        headView.findViewById(R.id.ll_vip).setOnClickListener(this);

        View bottomView = View.inflate(getActivity(), R.layout.fragment_mebottm, null);
        gridView_me = (MosaicGridView) bottomView.findViewById(R.id.gridView_me);
        gridView_me.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                intent.putExtra("goodsId", mdata.get(position).getGoodsid());
                startActivity(intent);
            }
        });
        listView_me.addHeaderView(headView);
        listView_me.addHeaderView(bottomView);
        listView_me.setAdapter(new NullAdapter(getActivity(), new ArrayList<String>(), R.layout.item_null));
        bga_refreshLayout_me.setPullDownRefreshEnable(false);//禁止下拉刷新操作
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        meAdapter = new MeFragmentAdapter(getActivity(), mdata, R.layout.item_mefragment_good);
        gridView_me.setAdapter(meAdapter);
        if (TextStringUtils.isEmpty(SharedPreferencesUtil.getInstance(App.getInstance()).getString("token")) == false && SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") != 0) {
            //获取用户个人信息
            presenter.loadUserInfor();
        }
        presenter.loadGoodInfor(7, pageNo, pageSize);
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 4 || msg.getType() == 6 || msg.getType() == 30 || msg.getType() == 26 ||
                        msg.getType() == 27 || msg.getType() == 28 || msg.getType() == 29 || msg.getType() == 33 || msg.getType() == 63) {
                    //个人中心需要重新请求数据
                    presenter.loadUserInfor();
                } else if (msg.getType() == 53) {//退出登录操作
                    iv_meHead.setImageResource(R.mipmap.icon_nogin);
                    ll_noLoginState.setVisibility(View.VISIBLE);
                    ll_loginState.setVisibility(View.GONE);
                    SharedPreferencesUtil.getInstance(getActivity()).setString("token", "");
                    SharedPreferencesUtil.getInstance(getActivity()).setInt("member_id", 0);
                    LoginBean loginBean = new LoginBean();
                    loginBean.setData(new UserInfor());
                    setContent(loginBean);
                    loginState = false;
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "退出登录成功");
                }
            }
        };
        observable.subscribe(subscriber);
        clickMsgObservable = RxBus.get().register("meClick", RxClickMsg.class);
        Subscriber<RxClickMsg> subscriber1 = new Subscriber<RxClickMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxClickMsg rxClickMsg) {
                if (rxClickMsg.getType() == 3) {
                    if (loginState) {    //登录就去获取状态
                        HashMap<String, String> params = new HashMap<>();
                        params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id") + "");
                        params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
                        presenter.getApplyStatus(params);
                        showLoading("");
                    }
                }
            }
        };
        clickMsgObservable.subscribe(subscriber1);
        refreshMsgObservable = RxBus.get().register("refreshmsg", RefreshMsg.class);
        Subscriber<RefreshMsg> subscriber2 = new Subscriber<RefreshMsg>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg msg) {
                if (msg.getType() == 0) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id") + "");
                    params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
                    presenter.getApplyStatus(params);
                    showLoading("");
                }
            }
        };
        refreshMsgObservable.subscribe(subscriber2);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MeFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case tv_noLoginState://未登录状态
                mIntent = new Intent(getActivity(), LoginActivity.class);
                mIntent.putExtra("key", "meFragment");
                startActivity(mIntent);
                break;
            case R.id.ll_meEdit://进行编辑个人信息界面
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), UserInformationActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.main_order://进入订单界面
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), OrderCenterActivity.class);
                    mIntent.putExtra("key", 0);
                    startActivity(mIntent);
                }
                break;
            case R.id.waitpayment://进入所有订单
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), OrderCenterActivity.class);
                    mIntent.putExtra("key", 0);
                    startActivity(mIntent);
                }
                break;
            case R.id.waitdelivery://待付款
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), OrderCenterActivity.class);
                    mIntent.putExtra("key", 1);
                    startActivity(mIntent);
                }
                break;
            case R.id.waitreceive://已付款
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), OrderCenterActivity.class);
                    mIntent.putExtra("key", 2);
                    startActivity(mIntent);
                }
                break;
            case R.id.waitevaluation://交易完成
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), OrderCenterActivity.class);
                    mIntent.putExtra("key", 3);
                    startActivity(mIntent);
                }
                break;
            case R.id.aftersale://售后
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    Intent intent = new Intent(getActivity(), AfterSalesCenterActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.custom_service:
                //                showLoading(Constants.progressName);
                //                presenter.getPhoneNum();
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
                break;
            case R.id.ll_shoppingcar:
                if (!loginState) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.myWeekShoot://我的周拍
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyWeekShootActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.myCustom://我的定制
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyCustomActivity.class);
                    startActivity(mIntent);
                }
                break;
            case businessCooperation://商家合作
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    //                    mIntent.putExtra(StringConfig.WEBVIEWURL, UrlConfig.PHILHARMONIC_HOST + "h5_contact.html");
                        mIntent = new Intent(getActivity(), BusinessActivity.class);
                        startActivity(mIntent);
                }
                break;
            case R.id.myCoupons://优惠券
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyCouponsActivity.class);
                    startActivity(mIntent);
                }
                break;
            case myEvaluation://我的评价
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyEvaluationActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.adressManagement://地址管理
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), ReceiptAddressActivtiy.class);
                    mIntent.putExtra("key", "MeFragment");
                    startActivity(mIntent);
                }
                break;
            case R.id.myCollection://我的收藏
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyCollectionActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.myCyclopedia://我的百科
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MineCycloActivity.class);
                    mIntent.putExtra("type", "cyclopedia");
                    startActivity(mIntent);
                }
                break;
            case R.id.myNew://我的新闻
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyNewsActivity.class);
                    mIntent.putExtra("type", "news");
                    startActivity(mIntent);
                }
                break;
            case R.id.my_browsinghistory://我的浏览记录
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), BrowsingHistoryActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.myIntegra://我的积分
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyIntegralActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.myMessage://我的消息
                if (loginState == false) {
                    ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), "未登录");
                } else {
                    mIntent = new Intent(getActivity(), MyMessageActivity.class);
                    startActivity(mIntent);
                }
                break;
            case R.id.ll_aboutApp://关于我们
                Intent intentAbout = new Intent(getActivity(), HelpActivity.class);
                intentAbout.putExtra("type", "aboutus");
                startActivity(intentAbout);
                break;
            case R.id.ll_wallet:
                mIntent = new Intent(getActivity(), StoreHomeActivity.class);
                startActivity(mIntent);
                break;
            case R.id.ll_vip:
                mIntent = new Intent(getActivity(), AllSpecialActivity.class);
                startActivity(mIntent);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isLoad == true) {
            pageNo++;
            showLoading(Constants.progressName);
            presenter.loadGoodInfor(7, pageNo, pageSize);
        }
        return false;
    }

    @Override
    public void loadUserInfor(LoginBean loginBean) {
        hideLoading();
        ImageLoaderUtils.loadImage(getActivity(), loginBean.getData().getFace(), iv_meHead);
        setContent(loginBean);
        ll_noLoginState.setVisibility(View.GONE);
        ll_loginState.setVisibility(View.VISIBLE);
        loginState = true;
        //登录成功去获取申请状态
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(getActivity()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(getActivity()).getString("token"));
        presenter.getApplyStatus(params);

    }

    private void setContent(LoginBean loginBean) {
        Log.e("返回值：", "进行退出登录操作:" + loginBean.toString());
        tv_cllectionCount.setText(loginBean.getData().getCllectionCount() + "");
        tv_integralBalance.setText(loginBean.getData().getIntegralBalance() + "");
        tv_msgCount.setText(loginBean.getData().getMsgCount() + "");
        tv_meUserID.setText("ID:" + SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id"));
        tv_meUserName.setText(loginBean.getData().getNickName());
        SharedPreferencesUtil.getInstance(getActivity()).setInt("CllectionCount", loginBean.getData().getCllectionCount());
        SharedPreferencesUtil.getInstance(getActivity()).setInt("IntegralBalance", loginBean.getData().getIntegralBalance());
        SharedPreferencesUtil.getInstance(getActivity()).setInt("MsgCount", loginBean.getData().getMsgCount());
    }

    @Override
    public void loadGoodInfor(List<HomeGoodInfo> goodInfos) {
        hideLoading();
        if (goodInfos != null && goodInfos.size() == pageSize) {
            isLoad = true;
        } else {
            isLoad = false;
        }
        mdata.addAll(goodInfos);
        meAdapter.notifyDataSetChanged();
        bga_refreshLayout_me.endLoadingMore();
    }


    @Override
    public void fail(LoginBean loginBean) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), loginBean.getMsg());
    }

    @Override
    public void callPhone(final String phone) {
        hideLoading();
        if (servicedialog == null) {
            servicedialog = new CommonDialog(getActivity(), phone, "确定", "取消");
        }
        servicedialog.show();
        servicedialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                servicedialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);

            }

            @Override
            public void cancleOnClick() {
                servicedialog.dismiss();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        RxBus.get().unregister("meClick", clickMsgObservable);
        RxBus.get().unregister("refreshmsg", refreshMsgObservable);
    }

    @Override
    public void getStatusError(String message) {

    }

    @Override
    public void getStatusSucess(ApplyStatusReturn applyStatusReturn) {
        hideLoading();
        if (applyStatusReturn.getCode() == 0) { //成功
            if (applyStatusReturn.getData().getDisabled() == -2) {
                tvCop.setText("商家合作");
            }  else {

                tvCop.setText("我是商家");
            }
        } else if (applyStatusReturn.getCode() == 1) { //失败
            Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();
        } else if (applyStatusReturn.getCode() == 2) { //未登录
            Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
        }
    }
}
