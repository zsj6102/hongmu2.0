package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.view.fragments.mine.MyCustomFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：我的定制
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/27 18 07
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_tpi
)
public class MyCustomActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.base_tab_layout)
    TabLayout base_tab_layout;

    @Bind(R.id.jvp_base)
    NoScrollViewPager jvp_base;

    private Observable<RxBusMsg> observable;

    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        tv_main_title.setText("我的定制");
        iv_back.setOnClickListener(this);
        for (int i = 0; i < Constants.MyCustom.length; i++) {
            base_tab_layout.addTab(base_tab_layout.newTab().setText(Constants.MyCustom[i]));
        }
        jvp_base.setAdapter(new OrderCenterPageAdapter(getSupportFragmentManager()));
        jvp_base.setOffscreenPageLimit(4);
        base_tab_layout.setupWithViewPager(jvp_base);
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final RxBusMsg msg) {
                if (msg.getType() == 45) {//支付宝支付成功
                    showMsg("支付成功", msg.getType());
                } else if (msg.getType() == 46) {//支付宝支付失败
                    showMsg("支付失败", msg.getType());
                } else if (msg.getType() == 47) {//微信支付成功
                    showMsg("支付成功", msg.getType());
                } else if (msg.getType() == 48) {//微信支付失败
                    showMsg("支付失败", msg.getType());
                } else if (msg.getType() == 54) {//银联支付成功
                    showMsg("支付成功", msg.getType());
                } else if (msg.getType() == 55) {//银联支付失败
                    showMsg("支付失败", msg.getType());
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void showMsg(String msg, int type) {
        ToastTools.showShort(this, msg);
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(50);
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class OrderCenterPageAdapter extends FragmentStatePagerAdapter {

        public OrderCenterPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyCustomFragment fragment;
            if (position == 0) {
                fragment = MyCustomFragment.getIntance("0");//全部
            } else if (position == 1) {
                fragment = MyCustomFragment.getIntance("1");//定制中
            } else if (position == 2) {
                fragment = MyCustomFragment.getIntance("2");//定制完成
            } else {
                fragment = MyCustomFragment.getIntance("3");//定制完成
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Constants.MyCustom[position
                    % Constants.MyCustom.length];
        }

        @Override
        public int getCount() {
            return Constants.MyCustom.length;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
