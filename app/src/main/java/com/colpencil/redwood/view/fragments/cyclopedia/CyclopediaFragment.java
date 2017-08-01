package com.colpencil.redwood.view.fragments.cyclopedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CategoryItem;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.cyclopedia.TagPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.ShoppingCartActivity;
import com.colpencil.redwood.view.activity.cyclopedia.PostCyclopediaActivity;
import com.colpencil.redwood.view.activity.home.CategoryActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.ITagView;
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
 * @author 陈宝
 * @Description:首页-百科
 * @Email DramaScript@outlook.com
 * @date 2016/7/12
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_cyclopedia
)
public class CyclopediaFragment extends ColpencilFragment implements ITagView {

    @Bind(R.id.header1)
    RelativeLayout header1;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.search_header_hint)
    EditText et_search;
    @Bind(R.id.totop_iv)
    ImageView totop_iv;
    @Bind(R.id.iv_post)
    ImageView iv_post;

    private TagPresenter present;
    private MyPageAdapter adapter;
    private String keywood;
    private Observable<RxBusMsg> observable;

    @Override
    protected void initViews(View view) {
        header1.setBackgroundColor(getResources().getColor(R.color.main_brown));
        totop_iv.setImageResource(R.mipmap.totop_icon);
        iv_post.setImageResource(R.mipmap.release_icon);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keywood = et_search.getText().toString();
                    GoodBusMsg msg = new GoodBusMsg();
                    msg.setType("search");
                    msg.setNorm(keywood);
                    RxBus.get().post(StringConfig.GOODSBUS, msg);
                    if (!TextUtils.isEmpty(keywood)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DaoUtils.saveHistory(6, keywood, getActivity());
                            }
                        }).start();
                    }
                }
                viewpager.setCurrentItem(0);
                return false;
            }
        });
        initBus();
    }
    @Override
    public void loadData() {
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean("isLogin", false)) {
            present.loadMyTag();
        } else {
            present.loadTag(2);
        }
    }
    private void initBus() {
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if (rxBusMsg.getType() == 56) {
                    viewpager.setCurrentItem(rxBusMsg.getPosition());
                } else if (rxBusMsg.getType() == 4 || rxBusMsg.getType() == 58 || rxBusMsg.getType() == 63) {
                    present.loadMyTag();
                } else if (rxBusMsg.getType() == 53) {
                    present.loadTag(2);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new TagPresenter();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadTag(List<CategoryVo> result) {
        adapter = new MyPageAdapter(getChildFragmentManager());
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCat_id("");
        categoryVo.setCat_name("全部");
        categoryVo.setCat_child(new ArrayList<CategoryItem>());
        adapter.addFragment(AllListFragment.newInstance(categoryVo), categoryVo.getCat_name());
        tabLayout.addTab(tabLayout.newTab().setText(categoryVo.getCat_name()));
        if (!ListUtils.listIsNullOrEmpty(result)) {
            for (CategoryVo cate : result) {
                adapter.addFragment(HomeListFragment.newInstance(cate), cate.getCat_name());
                tabLayout.addTab(tabLayout.newTab().setText(cate.getCat_name()));
            }
        }
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                GoodBusMsg msg = new GoodBusMsg();
                msg.setType("refreshState");
                RxBus.get().post(StringConfig.GOODSBUS, msg);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void loadError(String msg, int code) {

    }

    @OnClick(R.id.search_header_car)
    void onClick(){
        if(SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)){
            Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    @OnClick(R.id.iv_post)
    void post() {
        if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(getActivity(), PostCyclopediaActivity.class);
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    private void showDialog() {
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

    @OnClick(R.id.totop_iv)
    void totop() {
        GoodBusMsg msg = new GoodBusMsg();
        msg.setType(StringConfig.SMOOTHTOTOP);
        RxBus.get().post(StringConfig.GOODSBUS, msg);
    }

    @OnClick(R.id.ll_iv)
    void cateOnClick() {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
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
