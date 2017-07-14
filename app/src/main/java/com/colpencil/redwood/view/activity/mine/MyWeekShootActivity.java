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
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.view.fragments.mine.MyWeekShootFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import butterknife.Bind;

/**
 * 描述：我的竞拍
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/27 18 07
 */
@ActivityFragmentInject(
        contentViewId = R.layout.base_tpi
)
public class MyWeekShootActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.base_tab_layout)
    TabLayout base_tab_layout;

    @Bind(R.id.jvp_base)
    NoScrollViewPager jvp_base;

    @Override
    protected void initViews(View view) {
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        tv_main_title.setText("我的周拍");
        iv_back.setOnClickListener(this);
        for (int i = 0; i < Constants.MyWeekShoot.length; i++) {
            base_tab_layout.addTab(base_tab_layout.newTab().setText(Constants.MyWeekShoot[i]));
        }
        jvp_base.setAdapter(new OrderCenterPageAdapter(getSupportFragmentManager()));
        jvp_base.setOffscreenPageLimit(4);
        base_tab_layout.setupWithViewPager(jvp_base);
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
            MyWeekShootFragment fragment;
            if (position == 0) {
                fragment = MyWeekShootFragment.getIntance("0");//全部
            } else if (position == 1) {
                fragment = MyWeekShootFragment.getIntance("3");//竞拍中
            } else if (position == 2) {
                fragment = MyWeekShootFragment.getIntance("1");//竞拍成功
            } else {
                fragment = MyWeekShootFragment.getIntance("2");//竞拍失败
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Constants.MyWeekShoot[position
                    % Constants.MyWeekShoot.length];
        }

        @Override
        public int getCount() {
            return Constants.MyWeekShoot.length;
        }

    }

    ;
}
