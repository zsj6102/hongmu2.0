package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.home.WeelAuctionPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.discovery.WeekSortActivity;
import com.colpencil.redwood.view.fragments.WeekShootFragment;
import com.colpencil.redwood.view.impl.IWeekShootActivityView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 周拍信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 10 07
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_weekshoot
)
public class WeekShootActivity extends ColpencilActivity implements IWeekShootActivityView {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.jvp_weekShoot)
    NoScrollViewPager jvp_weekShoot;
    @Bind(R.id.tv_main_title)
    TextView tv_title;

    private WeekShootPageAdapter adapter;
    private WeelAuctionPresenter presenter;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText("周拍");
        adapter = new WeekShootPageAdapter(getSupportFragmentManager());
        jvp_weekShoot.setOffscreenPageLimit(3);
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            presenter.loadWeekShoot();
        } else {
            presenter.loadTag();
        }
        initBus();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new WeelAuctionPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 58) {
                    presenter.loadWeekShoot();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public void loadTagSuccess(List<CategoryVo> tags) {
        removeView();
        adapter.addFragment(WeekShootFragment.newInstance(""), "全部");
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        if (!ListUtils.listIsNullOrEmpty(tags)) {
            for (CategoryVo tag : tags) {
                adapter.addFragment(WeekShootFragment.newInstance(tag.getCat_id() + ""), tag.getCat_name());
                tabLayout.addTab(tabLayout.newTab().setText(tag.getCat_name()));
            }
        }
        jvp_weekShoot.setAdapter(adapter);
        tabLayout.setupWithViewPager(jvp_weekShoot);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void removeView() {
        adapter.mFragments.clear();
        tabLayout.removeAllTabs();
    }

    @Override
    public void loadTagError() {

    }

    @OnClick(R.id.iv_back)
    void backOnclick() {
        finish();
    }

    @OnClick(R.id.iv_add)
    void addClick() {
        Intent intent = new Intent(this, WeekSortActivity.class);
        startActivity(intent);
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

    class WeekShootPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public WeekShootPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
}
