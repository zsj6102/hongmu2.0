package com.colpencil.redwood.view.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.home.MasterCraftsmanFragment;
import com.colpencil.redwood.view.fragments.mine.MineBrandFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import info.hoang8f.android.segmented.SegmentedGroup;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_mastercraftsman
)
/**
 * 名师名匠
 */
public class MasterCraftsmanActivity extends ColpencilActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.segment)
    SegmentedGroup segment;
    @Bind(R.id.btn_craftsman)
    RadioButton btn_craftsman;
    @Bind(R.id.btn_mine)
    RadioButton btn_mine;
    @Bind(R.id.btn_store)
    RadioButton btn_store;

    private MyPageAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();

    @Override
    protected void initViews(View view) {
        fragments.add(new MasterCraftsmanFragment());
        fragments.add(new MineBrandFragment());
        viewPager.setOffscreenPageLimit(3);
        adapter=new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        segment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(R.id.btn_craftsman==i){
                    viewPager.setCurrentItem(0);
                }else if(R.id.btn_mine==i){
                    viewPager.setCurrentItem(1);
                }else{
//                    viewPager.setCurrentItem(2);
                }
            }
        });

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    class MyPageAdapter extends FragmentPagerAdapter {
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
    }
}
