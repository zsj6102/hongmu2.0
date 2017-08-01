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
    private int type;
    public static MineBrandFragment newInstance(int type) {
        MineBrandFragment fragment = new MineBrandFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void loadData() {}
    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        vp.setOffscreenPageLimit(3);
        madapter = new MyPageAdapter(getChildFragmentManager());
        if(type == 1){     //速拍拍品动态
            madapter.addFragment(SupaiDynamicFragment.newInstance(1), "拍品动态");
        }
        else if(type == 2){   //品牌店铺动态
            madapter.addFragment(DynamicFragment.newInstance(2), "店铺动态");
        }else if(type == 3){    //名师店铺动态
            madapter.addFragment(DynamicFragment.newInstance(3), "店铺动态");
        }

        if(type == 1){    //速拍 我的名片夹
            madapter.addFragment(CardWallFragment.newInstance(1), "个人名片夹");
        }else if(type == 2 ){// 品牌   我的名片夹
            madapter.addFragment(CardWallFragment.newInstance(2), "店铺名片夹");
        }else if(type == 3){//名师 我的名片夹
            madapter.addFragment(CardWallFragment.newInstance(3), "店铺名片夹");
        }

        if(type == 1){       //速拍我的收藏
            madapter.addFragment(SupaiDynamicFragment.newInstance(11), "我的收藏");
        }
       else if(type == 2){         //品牌我的收藏
           madapter.addFragment(DynamicFragment.newInstance(21), "我的收藏");
       }else if(type == 3){     //名匠我的收藏
           madapter.addFragment(DynamicFragment.newInstance(31),"我的收藏");
       }

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
