package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.colpencil.redwood.R;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.view.fragments.mine.MineBrandFragment;
import com.colpencil.redwood.view.fragments.mine.SpeedFragemnt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_speedshot
)
/**
 * 速拍商区
 */
public class SpeedShotActivity extends ColpencilActivity{
    @Bind(R.id.ll_back)
    LinearLayout ll_back;
    @Bind(R.id.segment)
    SegmentedGroup segment;
    @Bind(R.id.btn_speed)
    RadioButton btn_speed;
    @Bind(R.id.btn_mine)
    RadioButton btn_mine;
    @Bind(R.id.btn_store)
    RadioButton btn_store;
    @Bind(R.id.viewpager)
    NoScrollViewPager viewpager;
    private MyPagerAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews(View view) {
        fragments.add(new SpeedFragemnt());
        fragments.add(MineBrandFragment.newInstance(1));
        viewpager.setOffscreenPageLimit(3);
        adapter=new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        /**
         * 不是商家是不显示我的店铺的方法
         */
        //        segment.removeViewAt(segment.getChildCount()-1);
//        segment.updateBackground();
//        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//        layoutParams.setMargins(0, 0, 0, 0);
//        segment.getChildAt(segment.getChildCount() - 1).setLayoutParams(layoutParams);
        segment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.btn_speed:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.btn_mine:
                        if (SharedPreferencesUtil.getInstance(getApplication()).getBoolean(StringConfig.ISLOGIN,false)) {
                            viewpager.setCurrentItem(1);
                        } else {
                            ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), "未登录");
                        }
                        break;
                    case R.id.btn_store:

                        break;
                }
            }
        });

    }

    @OnClick(R.id.ll_back)
    void back(){
        finish();
    }
    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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
