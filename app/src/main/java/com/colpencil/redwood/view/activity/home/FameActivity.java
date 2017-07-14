package com.colpencil.redwood.view.activity.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.FameItem;
import com.colpencil.redwood.view.fragments.home.FameItemFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_fame
)
public class FameActivity extends ColpencilActivity{
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.tab_layout)
    TabLayout tab_layout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private List<FameItem> FameItems=new ArrayList<>();
    private MyPageAdapter adapter;


    @Override
    protected void initViews(View view) {

        tv_main_title.setText("名师名匠");
        initData();
        viewPager.setOffscreenPageLimit(3);
        adapter=new MyPageAdapter(getSupportFragmentManager());
        initTab();
        viewPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewPager);
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setCurrentItem(0);

    }

    private void initData() {
        for(int i=0;i<8;i++){
            FameItem FameItem=new FameItem();
            FameItem.setCat_name("测试"+i);
            FameItem.setCat_id(i);
            FameItems.add(FameItem);
        }
    }
    private void initTab() {
        for(FameItem fameItem:FameItems){
            tab_layout.addTab(tab_layout.newTab().setText(fameItem.getCat_name()));
            adapter.addFragment(FameItemFragment.newInstance(fameItem.getCat_id()),fameItem.getCat_name());
        }
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
