package com.colpencil.redwood.view.fragments.mine;

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
import com.colpencil.redwood.view.impl.AllAuctionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_new_product
)
public class AllCardWallFragment  extends ColpencilFragment implements AllAuctionView {
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager vp;
    @Bind(R.id.iv_add)
    ImageView iv_add;
    private  MyPageAdapter mAdapter;
    private AllAuctionPresent allAuctionPresent;
    private List<GoodsTypeInfo> goodsTypeInfoList=new ArrayList<>();
    private int type;
    public static AllCardWallFragment newInstance(int type ){
        AllCardWallFragment fragment = new AllCardWallFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void loadSuccess() {
        hideLoading();
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
    }

    @Override
    public void loadFail(String message) {
       hideLoading();
    }

    @Override
    public void getGoodsType(GoodsTypeResult goodsTypeResult) {
        List<GoodsTypeInfo> list=goodsTypeResult.getData();
        goodsTypeInfoList.addAll(list);
        mAdapter = new MyPageAdapter(getChildFragmentManager());
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
    public void loadData() {
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
