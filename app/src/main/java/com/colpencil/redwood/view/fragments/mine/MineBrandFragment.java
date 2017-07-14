package com.colpencil.redwood.view.fragments.mine;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_mine_brand
)
public class MineBrandFragment extends ColpencilFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager vp;

    private MyPageAdapter madapter;

    @Override
    protected void initViews(View view) {
        vp.setOffscreenPageLimit(3);
        madapter = new MyPageAdapter(getChildFragmentManager());
        madapter.addFragment(DynamicFragment.newInstance(0), "店铺动态");
        madapter.addFragment(CardWallFragment.newInstance(0), "店铺名片夹");
        madapter.addFragment(DynamicFragment.newInstance(1), "店铺名片夹");
        tabLayout.addTab(tabLayout.newTab().setText("店铺动态"));
        tabLayout.addTab(tabLayout.newTab().setText("店铺名片夹"));
        tabLayout.addTab(tabLayout.newTab().setText("我的收藏"));
        vp.setAdapter(madapter);
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyPageAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> titles = new ArrayList<>();

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
