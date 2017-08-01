package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodsTypeInfo;
import com.colpencil.redwood.bean.result.GoodsTypeResult;
import com.colpencil.redwood.present.home.AllAuctionPresent;
import com.colpencil.redwood.view.fragments.home.FameItemFragment;
import com.colpencil.redwood.view.fragments.mine.AllCardWallFragment;
import com.colpencil.redwood.view.fragments.mine.CardWallFragment;
import com.colpencil.redwood.view.fragments.mine.CardWallItemFragment;
import com.colpencil.redwood.view.impl.AllAuctionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static android.R.attr.type;

@ActivityFragmentInject(contentViewId = R.layout.activity_halloffame)
public class HallofFameActivity extends ColpencilActivity implements AllAuctionView {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager vp;
    @Bind(R.id.iv_add)
    ImageView iv_add;
    private MyPageAdapter mAdapter;
    private AllAuctionPresent allAuctionPresent;
    private List<GoodsTypeInfo> goodsTypeInfoList=new ArrayList<>();
    private int type = 4;
    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getGoodsType(GoodsTypeResult goodsTypeResult) {
        List<GoodsTypeInfo> list=goodsTypeResult.getData();
        goodsTypeInfoList.addAll(list);
        mAdapter = new  MyPageAdapter(getSupportFragmentManager());
        for (int i=0;i<goodsTypeInfoList.size();i++) {
            tabLayout.addTab(tabLayout.newTab().setText(goodsTypeInfoList.get(i).getName()));

            mAdapter.addFragment(CardWallItemFragment.newInstance(goodsTypeInfoList.get(i).getCat_id(),type), goodsTypeInfoList.get(i).getName());
        }

        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        hideLoading();
    }

    @Override
    protected void initViews(View view) {
        allAuctionPresent.getGoodsType();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        allAuctionPresent=new AllAuctionPresent();
        return allAuctionPresent;
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