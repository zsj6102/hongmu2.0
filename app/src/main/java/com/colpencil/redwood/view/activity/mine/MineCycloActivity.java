package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.CycloListFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:我的百科
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_mine_cyclopedia
)
public class MineCycloActivity extends ColpencilActivity {

    @Bind(R.id.common_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.common_vp)
    NoScrollViewPager viewPager;
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    private MyPagerAdapter adapter;

    @Override
    protected void initViews(View view) {
        tv_title.setText(getString(R.string.my_cyclopedia));
        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CycloListFragment.newInstance("2"), getString(R.string.tv_under_review));
        adapter.addFragment(CycloListFragment.newInstance("0"), getString(R.string.tv_passed));
        adapter.addFragment(CycloListFragment.newInstance("1"), getString(R.string.tv_publish_failed));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv_under_review)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv_passed)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv_publish_failed)));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
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
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
