package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllAuctionItem;
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
        contentViewId = R.layout.fragment_allauction
)
public class AllAuctionFragment extends ColpencilFragment  implements AllAuctionView {
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private MyPageAdapter adapter;
    private AllAuctionPresent allAuctionPresent;
    private List<GoodsTypeInfo> goodsTypeInfoList=new ArrayList<>();
    @Override
    protected void initViews(View view) {

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
    public void loadData() {
        showLoading("加载中...");
        allAuctionPresent.getGoodsType();
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
        adapter=new MyPageAdapter(getChildFragmentManager());
        for(int i=0;i<goodsTypeInfoList.size();i++){
            adapter.addFragment(AllAuctionItemFragment.newInstance(goodsTypeInfoList.get(i).getCat_id()),goodsTypeInfoList.get(i).getName());
            tab_layout.addTab(tab_layout.newTab().setText(goodsTypeInfoList.get(i).getName()));
        }
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(3);
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
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

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }
    }
}
