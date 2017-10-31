package com.colpencil.redwood.view.fragments.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AdInfo;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.present.SpeedPresent;
import com.colpencil.redwood.view.fragments.mine.NewProductFragment;
import com.colpencil.redwood.view.impl.SpeedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_mastercraftsman
)
public class MasterCraftsmanFragment extends ColpencilFragment implements SpeedView {

    @Bind(R.id.craftsmanbanner)
    Banner banner;
    @Bind(R.id.craftsmantablayout)
    TabLayout tab_layout;

    @Bind(R.id.craftsmanviewpager)
    NoScrollViewPager viewpager;

    private MyPageAdapter adapetr;
    private SpeedPresent speedPresent;
    @Override
    protected void initViews(View view) {

        adapetr=new MyPageAdapter(getChildFragmentManager());
        adapetr.addFragent(new NewProductFragment(), "最新出品");
        adapetr.addFragent(new CraftsmanCardFragment(), "匠师名片墙");
        viewpager.setAdapter(adapetr);
        viewpager.setOffscreenPageLimit(2);
        tab_layout.addTab(tab_layout.newTab().setText("最新出品"));
        tab_layout.addTab(tab_layout.newTab().setText("匠师名片墙"));
        tab_layout.setupWithViewPager(viewpager);
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
    public void loadData() {
        showLoading("加载中...");
        speedPresent.getAd("mingjiang");
    }
    @Override
    public void getAd(AdResult adResult) {
        List<BannerVo> adInfoList=adResult.getData();
        List<String> imgUrls = new ArrayList<>();
        for(int i=0;i<adInfoList.size();i++){
            imgUrls.add(adInfoList.get(i).getUrl());
        }
        banner.setImageLoader(new MyImageLoader());
        banner.setImages(imgUrls);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//指示器模式
        banner.start();
        hideLoading();
    }

    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments=new ArrayList<>();
        private List<String> titles=new ArrayList<>();
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
        public void addFragent(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }
    }
}
