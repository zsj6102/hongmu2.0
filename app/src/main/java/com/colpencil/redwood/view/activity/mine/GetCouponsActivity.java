package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.GetCouponFragment;
import com.colpencil.redwood.view.fragments.mine.GetVoucherFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：获取优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/1 12 02
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_mycoupons
)
public class GetCouponsActivity extends ColpencilActivity {

    @Bind(R.id.myCoupons_header)
    RelativeLayout toolbar;
    @Bind(R.id.myCoupons_viewpager)
    ViewPager vp;
    @Bind(R.id.myCoupons_segmentgroup)
    RadioGroup group;
    @Bind(R.id.rb_can)
    RadioButton rb_can;
    @Bind(R.id.rb_no)
    RadioButton rb_no;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] title = {"代金券", "优惠券"};

    @Override
    protected void initViews(View view) {
        toolbar.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        fragments.add(new GetVoucherFragment());//代金券
        fragments.add(new GetCouponFragment());//优惠券
        vp.setOffscreenPageLimit(3);
        MyCouponsPageAdapter adapter = new MyCouponsPageAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        setupViewPager();
    }

    private void setupViewPager() {
        if (getIntent().getIntExtra("type", 0) == 0) {        //代金券
            rb_can.setChecked(true);
            vp.setCurrentItem(0, false);
        } else {        //优惠券
            rb_no.setChecked(true);
            vp.setCurrentItem(1, false);
        }
        rb_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.check(R.id.rb_can);
                vp.setCurrentItem(0, false);
            }
        });
        rb_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.check(R.id.rb_no);
                vp.setCurrentItem(1, false);
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

    @OnClick(R.id.myCoupons_header_back)
    void backClick() {
        finish();
    }

    class MyCouponsPageAdapter extends FragmentPagerAdapter {

        public MyCouponsPageAdapter(FragmentManager fm) {
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
            return title[position];
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
