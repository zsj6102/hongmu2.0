package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.DragTopLayout;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.SpeedPresent;
import com.colpencil.redwood.view.activity.classification.CommoditySearchActivity;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.home.ProductdetailsActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.BusinessActivity;
import com.colpencil.redwood.view.activity.mine.PublishStoreActivity;
import com.colpencil.redwood.view.impl.SpeedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_speed)
public class SpeedFragemnt extends ColpencilFragment implements SpeedView {
    @Bind(R.id.circle_banner)
    Banner banner;

    @Bind(R.id.tabsLayout)
    TabLayout tablayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.drag_layout)
    DragTopLayout dragLayout;
    @Bind(R.id.bannerImage)
    ImageView imageView;
    private MyPageAdapter adapter;
    private SpeedPresent speedPresent;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private int store_id;
    private int store_type;
    private int disabled;

    @Override
    protected void initViews(View view) {
        dragLayout.setOverDrag(false);
        adapter = new MyPageAdapter(getChildFragmentManager());
        adapter.addFragment(new AllAuctionFragment(), "所有拍品");
        adapter.addFragment(AllCardWallFragment.newInstance(1), "速拍名片墙");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tablayout.addTab(tablayout.newTab().setText("所有拍品"));
        tablayout.addTab(tablayout.newTab().setText("速拍名片墙"));
        tablayout.setupWithViewPager(viewPager);
        initBus();
        banner.setImageLoader(new MyImageLoader());
    }

    private void initBus() {
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if (rxBusMsg.getType() == 95) {
                    store_id = rxBusMsg.getItem_id();
                    disabled = rxBusMsg.getStatus();
                    store_type = rxBusMsg.getCpns_id();
                }
            }
        };
        observable.subscribe(subscriber);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
        }
    }

    @Override
    public void loadData() {
        showLoading("加载中...");
        speedPresent.getAd("supai");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        speedPresent = new SpeedPresent();
        return speedPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.totop_iv)
    void totop() {

        dragLayout.openTopView(true);
        RxClickMsg msg = new RxClickMsg();
        msg.setType(100);
        RxBus.get().post("totop", msg);
    }

    @OnClick(R.id.post_iv)
    void postGood() {
        if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
            if (disabled == 1) {
                Intent intent = new Intent(getActivity(), PublishStoreActivity.class);
                intent.putExtra("type", store_type + "");
                intent.putExtra("id", store_id + "");
                getActivity().startActivity(intent);
            } else if (disabled == 0) {
                ToastTools.showShort(getActivity(), "您的店铺正在审核中，暂时不能发布商品");
            } else if (disabled == 2) {
                ToastTools.showShort(getActivity(), "您的店铺已被冻结，暂时不能发布商品");
            } else if (disabled == -1 || disabled == -2) {
                //disabled :-2 未成为商家  0 审核中  -1 未通过审核 2 冻结  1运营中
                showDialog2();

            }

        } else {
            showDialog();
        }

    }

    private void showDialog2() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "亲，只有成为商家才可以发布商品哦!", "成为商家", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(getActivity(), BusinessActivity.class);
                getActivity().startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                intent();
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void intent() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(StringConfig.REQUEST_CODE, 100);
        startActivityForResult(intent, Constants.REQUEST_LOGIN);
    }

    public void onEvent(Boolean b) {
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
    public void loadSuccess() {
        hideLoading();
    }

    @Override
    public void loadFail(String message) {
        hideLoading();
    }

    @Override
    public void getAd(AdResult adResult) {
        final List<BannerVo> adInfoList = adResult.getData();

        if (!ListUtils.listIsNullOrEmpty(adInfoList)) {
            if (adInfoList.size() >= 2) {
                List<String> imgUrls = new ArrayList<>();
                for (BannerVo bannerVo : adInfoList) {
                    imgUrls.add(bannerVo.getUrl());
                }
                banner.setImages(imgUrls);
                banner.start();

                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                banner.setVisibility(View.VISIBLE);
                banner.post(new Runnable() {
                    @Override
                    public void run() {
                        int width = banner.getWidth();
                        int height;
                        if ((int) adInfoList.get(0).getImage_scale() != 0) {
                            height = (int) (width / adInfoList.get(0).getImage_scale());
                            banner.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                        }
                    }
                });
            } else {
                banner.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                ImageLoaderUtils.loadImage(mContext, adInfoList.get(0).getUrl(), imageView);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        int width = imageView.getWidth();
                        int height;
                        if ((int) adInfoList.get(0).getImage_scale() != 0) {
                            height = (int) (width / adInfoList.get(0).getImage_scale());
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                        }
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BannerVo vo = adInfoList.get(0);
                        voClick(vo);
                    }
                });
            }
        } else {
            banner.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }


        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerVo vo = adInfoList.get(position - 1);
                voClick(vo);
            }
        });

        hideLoading();
    }

    private void voClick(BannerVo vo) {
        if ("good".equals(vo.getType())) {
            Intent intent = new Intent();
            intent.setClass(mContext, GoodDetailActivity.class);
            intent.putExtra("goodsId", Integer.valueOf(vo.getGoodsId()));
            mContext.startActivity(intent);
        } else if ("link".equals(vo.getType())) {
            Intent intent = new Intent();
            intent.setClass(mContext, MyWebViewActivity.class);
            intent.putExtra("webviewurl", vo.getHtmlurl());
            intent.putExtra("type", "banner");
            mContext.startActivity(intent);
        } else if ("zhoupai".equals(vo.getType())) {
            Intent intent = new Intent();
            intent.setClass(mContext, ProductdetailsActivity.class);
            intent.putExtra("goodsId", Integer.valueOf(vo.getGoodsId()));
            mContext.startActivity(intent);
        } else if ("cat".equals(vo.getType())) {
            Intent intent = new Intent(mContext, CommoditySearchActivity.class);
            intent.putExtra("child_cat_id", Integer.valueOf(vo.getGoodsId()));
            mContext.startActivity(intent);
        }

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
