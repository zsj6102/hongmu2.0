package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AdInfo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.present.SpeedPresent;
import com.colpencil.redwood.view.impl.SpeedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_brand_merchant
)
public class BrandMerchantFragment extends ColpencilFragment implements SpeedView {

    @Bind(R.id.circle_banner)
    Banner banner;
    @Bind(R.id.tabsLayout)
    TabLayout tablayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.drag_layout)
    DragTopLayout dragLayout;

    private MyPageAdapter mAdapter;
    private SpeedPresent speedPresent;

    @Override
    protected void initViews(View view) {

        dragLayout.setOverDrag(false);
        mAdapter = new MyPageAdapter(getChildFragmentManager());
        mAdapter.addFragment(NewProductFragment.newInstance(2), "新品推荐");//品牌
        mAdapter.addFragment(AllCardWallFragment.newInstance(2), "店铺名片墙");
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        tablayout.addTab(tablayout.newTab().setText("新品推荐"));
        tablayout.addTab(tablayout.newTab().setText("店铺名片墙"));
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        speedPresent=new SpeedPresent();
        return speedPresent;
    }
    @OnClick(R.id.totop_iv)
    void totop() {

        dragLayout.openTopView(true);
        RxClickMsg msg = new RxClickMsg();
        msg.setType(100);
        RxBus.get().post("totop",msg);
    }
    public void onEvent(Boolean b){
        dragLayout.setTouchMode(b);
    }
    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

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
        speedPresent.getAd("pinpai");
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
