package com.colpencil.redwood.view.activity.discovery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryItem;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.cyclopedia.TagPresenter;
import com.colpencil.redwood.view.activity.home.CategoryActivity;
import com.colpencil.redwood.view.fragments.discovery.ReCyclopediaFragment;
import com.colpencil.redwood.view.impl.ITagView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:推荐百科
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_recommend_cyclopedia)
public class ReCyclopediaActivity extends ColpencilActivity implements ITagView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.recommend_vp)
    NoScrollViewPager vp;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    private MyPageAdapter adapter;
    private TagPresenter presenter;
    private CategoryVo categoryVo;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText(getString(R.string.recommend_cyclopedia));
        categoryVo = new CategoryVo();
        categoryVo.setCat_id("");
        categoryVo.setCat_name("全部");
        categoryVo.setCat_child(new ArrayList<CategoryItem>());
        showLoading(Constants.progressName);
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            presenter.loadMyTag();
        } else {
            presenter.loadTag(2);
        }
        initBus();
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
                if (tagMsg.getType() == 58) {
                    presenter.loadMyTag();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new TagPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadTag(List<CategoryVo> taglist) {
        hideLoading();
        removeAllView();
        adapter = new MyPageAdapter(getSupportFragmentManager());
        adapter.addFragment(ReCyclopediaFragment.newInstance(categoryVo), categoryVo.getCat_name());
        tabLayout.addTab(tabLayout.newTab().setText(categoryVo.getCat_name()));
        if (!ListUtils.listIsNullOrEmpty(taglist)) {
            for (CategoryVo cate : taglist) {
                adapter.addFragment(ReCyclopediaFragment.newInstance(cate), cate.getCat_name());
                tabLayout.addTab(tabLayout.newTab().setText(cate.getCat_name()));
            }
        }
        vp.setOffscreenPageLimit(3);
        vp.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(vp);
    }

    @Override
    public void loadError(String msg, int code) {
        hideLoading();
        if (code == 3) {
            presenter.loadTag(2);
        }
    }

    private void removeAllView() {
        tabLayout.removeAllTabs();
    }

    @OnClick(R.id.ll_iv)
    void cateClick() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public final List<Fragment> mFragments = new ArrayList<>();
        public final List<String> titles = new ArrayList<>();

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            titles.add(title);
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
