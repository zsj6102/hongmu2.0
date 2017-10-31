package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryItem;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.HomeGoodsPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.NewShopingCartActivity;
import com.colpencil.redwood.view.activity.home.CategoryActivity;
import com.colpencil.redwood.view.activity.home.CodeActivity;
import com.colpencil.redwood.view.activity.home.SearchActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.IHomeGoodsView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;


/**
 * 二期首页
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_homegoods)
public class HomeGoodsFragment extends ColpencilFragment implements IHomeGoodsView {
    @Bind(R.id.myhead)
    RelativeLayout rl_header;
    @Bind(R.id.search_header_hint)
    TextView input_hint;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.search_header_code)
    ImageView iv_code;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private HomeGoodsPresenter presenter;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private MyPageAdapter pageAdapter;

    @Override
    public void loadTag(List<CategoryVo> taglist) {

        final List<Fragment> fragments = new ArrayList<>();
        final List<String> titles = new ArrayList<>();
        tabLayout.removeAllTabs();
        titles.clear();
        fragments.clear();
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCat_name("推荐");
        categoryVo.setCat_child(new ArrayList<CategoryItem>());
        titles.add("推荐");
        fragments.add(new HomePageFragment());
        tabLayout.addTab(tabLayout.newTab().setText(categoryVo.getCat_name()));
        if (!ListUtils.listIsNullOrEmpty(taglist)) {
            for (CategoryVo cate : taglist) {
                titles.add(cate.getCat_name());
                fragments.add(HomeGoodsPageFragment.newInstance(cate));
                tabLayout.addTab(tabLayout.newTab().setText(cate.getCat_name()));
            }
        }
        viewpager.setOffscreenPageLimit(3);
        pageAdapter = new MyPageAdapter(getChildFragmentManager(), fragments, titles);
        viewpager.setAdapter(pageAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //        tabLayout.setupWithViewPager(viewpager);
        //设置监听,注意:如果设置了setOnTabSelectedListener,则setupWithViewPager不会生效
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                RxClickMsg msg = new RxClickMsg();
                msg.setType(3000);//tab点击
                msg.setPosition(tab.getPosition());
                RxBus.get().post("refreshmsg", msg);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                RxClickMsg msg = new RxClickMsg();
                msg.setType(3000);//tab点击
                msg.setPosition(tab.getPosition());
                RxBus.get().post("refreshmsg", msg);
            }
        });

        hideLoading();

    }

    @Override
    public void loadError(String msg) {

    }

    @Override
    protected void initViews(View view) {
        rl_header.setBackgroundColor(getResources().getColor(R.color.main_brown));
        input_hint.setText("搜你想搜的");
        iv_code.setVisibility(View.VISIBLE);
        initBus();
    }

    @Override
    protected void loadData() {
        showLoading("");
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            presenter.loadMyGoodsTag();
        } else {
            presenter.loadAllGoodsTag();
        }
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
            public void onNext(RxBusMsg tagMsg) {
                if (tagMsg.getType() == 58 || tagMsg.getType() == 4 || tagMsg.getType() == 63 || tagMsg.getType() == 200) {
                    presenter.loadMyGoodsTag();
                } else if (tagMsg.getType() == 53) {
                    presenter.loadAllGoodsTag();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HomeGoodsPresenter();
        return presenter;
    }

    @OnClick(R.id.search_header_ll)
    void searchClick() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("from","homegoods");
        startActivity(intent);
    }

    @OnClick(R.id.ll_iv)
    void cateOnClick() {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra("from","HomeGoodsFragment");
        startActivity(intent);
    }

    @OnClick(R.id.search_header_code)
    void codeClick() {
//        SkinManager.getInstance().NightMode();
        Intent intent = new Intent(getActivity(), CodeActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.search_header_car)
    void onClick() {
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(getActivity(), NewShopingCartActivity.class);
            startActivity(intent);
        } else {
            final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra(StringConfig.REQUEST_CODE, 100);
                    startActivityForResult(intent, Constants.REQUEST_LOGIN);
                    dialog.dismiss();
                }

                @Override
                public void cancleOnClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public List<Fragment> mFragments = new ArrayList<>();
        public List<String> titles = new ArrayList<>();

        public MyPageAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> titles) {
            super(fm);
            this.mFragments = mFragments;
            this.titles = titles;
        }

        public void addTitle(String title) {
            //            mFragments.add(fragment);
            titles.add(title);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //处理position。让数组下标落在[0,fragmentList.size)中，防止越界
            position = position % mFragments.size();
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
