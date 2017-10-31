package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.CardWallFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

@ActivityFragmentInject(
        contentViewId = R.layout.myfans_layout)
public class MyFansAndLike extends ColpencilActivity {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.tabsLayout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private MyAdapter pagerAdapter;
    private TabLayout.TabLayoutOnPageChangeListener listener;
    private List<String> titles = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        titles.add("我的关注");
        titles.add("我的粉丝");
        tv_main_title.setText("我的圈子");
        pagerAdapter = new MyAdapter(getSupportFragmentManager());
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tab_layout.setTabsFromPagerAdapter(pagerAdapter);
        listener = new TabLayout.TabLayoutOnPageChangeListener(tab_layout);
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = CardWallFragment.newInstance(5);
                    break;
                case 1:
                    fragment = CardWallFragment.newInstance(6);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
