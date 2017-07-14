package com.colpencil.redwood.view.activity.mine;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.BrandMerchantFragment;
import com.colpencil.redwood.view.fragments.mine.MineBrandFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_brand_store
)
/**
 * 品牌商区
 */
public class BrandStoreActivity extends ColpencilActivity implements OnClickListener, OnCheckedChangeListener {

    @Bind(R.id.viewpager)
    NoScrollViewPager viewpager;
    @Bind(R.id.segment)
    SegmentedGroup segment;
    @Bind(R.id.btn_brand)
    RadioButton btn_brand;
    @Bind(R.id.btn_mine)
    RadioButton btn_mine;

    private MyPagerAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        fragments.add(new BrandMerchantFragment());
        fragments.add(new MineBrandFragment());
        viewpager.setOffscreenPageLimit(3);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(0);
        segment.setOnCheckedChangeListener(this);
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

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
        switch (checkId) {
            case R.id.btn_brand:
                viewpager.setCurrentItem(0);
                break;
            case R.id.btn_mine:
                viewpager.setCurrentItem(1);
                break;
            case R.id.btn_store:

                break;
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
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
    }
}
