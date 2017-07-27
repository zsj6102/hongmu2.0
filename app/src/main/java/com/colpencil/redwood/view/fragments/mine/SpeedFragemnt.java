package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AdInfo;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.present.SpeedPresent;
import com.colpencil.redwood.view.impl.SpeedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;
import com.property.colpencil.colpencilandroidlibrary.Ui.ViewpagerCycle.ADInfo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.unionpay.mobile.android.global.a.A;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_speed
)
public class SpeedFragemnt extends ColpencilFragment implements SpeedView {
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.speedtabLayout)
    TabLayout speedtabLayout;
    @Bind(R.id.speedviewpager)
    NoScrollViewPager speedviewpager;
    private MyPageAdapter adapter;
    private SpeedPresent speedPresent;

    @Override
    protected void initViews(View view) {
        showLoading("加载中...");
        speedPresent.getAd("supai");
        adapter=new MyPageAdapter(getChildFragmentManager());
        adapter.addFragment(new AllAuctionFragment(),"所有拍品");
        adapter.addFragment(CardWallFragment.newInstance(11),"速拍名片墙");
        speedviewpager.setAdapter(adapter);
        speedviewpager.setOffscreenPageLimit(2);
        speedtabLayout.addTab(speedtabLayout.newTab().setText("所有拍品"));
        speedtabLayout.addTab(speedtabLayout.newTab().setText("速拍名片墙"));
        speedtabLayout.setupWithViewPager(speedviewpager);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        speedPresent=new SpeedPresent();
        return speedPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getAd(AdResult adResult) {
        List<AdInfo> adInfoList=adResult.getData();
        List<String> imgUrls = new ArrayList<>();
        for(int i=0;i<adInfoList.size();i++){
            imgUrls.add(adInfoList.get(i).getAtturl());
        }
        banner.setImageLoader(new MyImageLoader());
        banner.setImages(imgUrls);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
        banner.start();
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
