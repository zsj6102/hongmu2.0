package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.FamousStoreFragment;
import com.colpencil.redwood.view.fragments.mine.MineBrandFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_brand_store)
public class FamousStoreActivity extends ColpencilActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

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
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
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

    @Override
    protected void initViews(View view) {
        fragments.add(new FamousStoreFragment());
        fragments.add(MineBrandFragment.newInstance(3));
        btn_brand.setText("名师名匠");
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

    @OnClick(R.id.ll_back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
