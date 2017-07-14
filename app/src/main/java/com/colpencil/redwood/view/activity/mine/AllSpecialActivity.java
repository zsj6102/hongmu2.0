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
import com.colpencil.redwood.view.fragments.mine.AllSpecialItemFragment;
import com.colpencil.redwood.view.impl.AllAuctionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_allspecial
)
/**
 * 所有专场
 */
public class AllSpecialActivity extends ColpencilActivity implements AllAuctionView {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager vp;
    @Bind(R.id.iv_add)
    ImageView iv_add;

    private AllSpecialActivity.MyPageAdapter mAdapter;
    private AllAuctionPresent allAuctionPresent;
    private List<GoodsTypeInfo> goodsTypeInfoList=new ArrayList<>();


    @Override
    protected void initViews(View view) {
        showLoading("加载中...");
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
        mAdapter = new AllSpecialActivity.MyPageAdapter(getSupportFragmentManager());
        for (int i=0;i<goodsTypeInfoList.size();i++) {
            tabLayout.addTab(tabLayout.newTab().setText(goodsTypeInfoList.get(i).getName()));
            mAdapter.addFragment(AllSpecialItemFragment.newInstance(goodsTypeInfoList.get(i).getCat_id()), goodsTypeInfoList.get(i).getName());
        }
        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        hideLoading();
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
