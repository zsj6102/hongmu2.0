package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.present.mine.MyCouponsPresenter;
import com.colpencil.redwood.view.fragments.mine.MyCouponsFragment;
import com.colpencil.redwood.view.fragments.mine.MyVoucherFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：我的优惠券
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/27 18 07
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_mycoupons
)
public class MyCouponsActivity extends ColpencilActivity {

    @Bind(R.id.myCoupons_header)
    RelativeLayout toolbar;
    @Bind(R.id.myCoupons_viewpager)
    NoScrollViewPager vp;
    @Bind(R.id.myCoupons_segmentgroup)
    RadioGroup group;
    @Bind(R.id.rb_can)
    RadioButton rb_can;
    @Bind(R.id.rb_no)
    RadioButton rb_no;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] title = {"代金券", "优惠券"};
    private MyCouponsPresenter presenter;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        toolbar.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        fragments.add(new MyVoucherFragment());//代金券
        fragments.add(new MyCouponsFragment());//优惠券

        vp.setOffscreenPageLimit(3);
        MyCouponsPageAdapter adapter = new MyCouponsPageAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        rb_can.setChecked(true);
        setupViewPager();
        presenter.getContent();
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 33) {//刷新可用券的数量
                    presenter.getContent();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void setupViewPager() {
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
        presenter = new MyCouponsPresenter();
        return presenter;
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
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
