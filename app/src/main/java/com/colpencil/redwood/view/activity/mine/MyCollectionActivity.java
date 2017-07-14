package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
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
import com.colpencil.redwood.present.mine.MyCollectionPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.fragments.mine.CyclopediaFragment;
import com.colpencil.redwood.view.fragments.mine.GoodsFragment;
import com.colpencil.redwood.view.fragments.mine.NewsFragment;
import com.colpencil.redwood.view.fragments.mine.PostFragment;
import com.colpencil.redwood.view.impl.IMyCollectionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：我的收藏
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 17 08
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_tpi
)
public class MyCollectionActivity extends ColpencilActivity implements View.OnClickListener, IMyCollectionView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_shoppingCartFinish)
    TextView removeAll;

    @Bind(R.id.base_tab_layout)
    TabLayout base_tab_layout;

    @Bind(R.id.jvp_base)
    NoScrollViewPager jvp_base;

    private MyCollectionPresenter presenter;

    private boolean clocked;

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
        tv_main_title.setText("我的收藏");
        removeAll.setText("清除");
        iv_back.setOnClickListener(this);
        removeAll.setOnClickListener(this);
        for (int i = 0; i < Constants.MyCollection.length; i++) {
            base_tab_layout.addTab(base_tab_layout.newTab().setText(Constants.MyCollection[i]));
        }
        jvp_base.setAdapter(new MyCollectionPageAdapter(getSupportFragmentManager()));
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
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 26 || msg.getType() == 27 || msg.getType() == 28 || msg.getType() == 29 || msg.getType() == 32) {//刷新数据
                    //个人中心需要重新请求数据
                    clocked = false;
                } else if (msg.getType() == 31) {
                    clocked = true;
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MyCollectionPresenter();
        return presenter;
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
            case R.id.tv_shoppingCartFinish://清除所有
                if (SharedPreferencesUtil.getInstance(MyCollectionActivity.this).getInt("CllectionCount", 0) == 0) {
                    ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "暂无收藏数据");
                } else if (clocked == false) {
                    clocked = true;
                    RxBusMsg msg = new RxBusMsg();
                    msg.setType(64);
                    RxBus.get().post("rxBusMsg", msg);
                }
                break;
        }
    }

    @Override
    public void removeResult(String code, String msg) {
        clocked = false;
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(MyCollectionActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    class MyCollectionPageAdapter extends FragmentStatePagerAdapter {

        public MyCollectionPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                GoodsFragment fragment = new GoodsFragment();
                return fragment;
            } else if (position == 1) {
                CyclopediaFragment fragment = new CyclopediaFragment();
                return fragment;
            } else if (position == 2) {
                NewsFragment fragment = new NewsFragment();
                return fragment;
            } else {
                PostFragment fragment = new PostFragment();
                return fragment;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Constants.MyCollection[position
                    % Constants.MyCollection.length];
        }

        @Override
        public int getCount() {
            return Constants.MyCollection.length;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

}
