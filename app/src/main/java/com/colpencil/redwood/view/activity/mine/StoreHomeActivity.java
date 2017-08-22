package com.colpencil.redwood.view.activity.mine;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.AboutHimFragment;
import com.colpencil.redwood.view.fragments.mine.AuctionFragment;
import com.colpencil.redwood.view.fragments.mine.EncyclopediasFragment;
import com.colpencil.redwood.view.fragments.mine.HoldingShelvesFragment;
import com.colpencil.redwood.view.fragments.mine.MinePostFragment;
import com.colpencil.redwood.view.fragments.mine.RatedFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author D * S
 * @date 2017/2/17
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_store_home)
public class StoreHomeActivity extends ColpencilActivity {


    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private List<String> titles = new ArrayList<>();
    private int type; // 0 商家主页   1 个人主页
    private int store_id; //商家或个人的id
    private MyAdapter pagerAdapter;
    private TabLayout.TabLayoutOnPageChangeListener listener;

    private PopupWindow popupWindow;

    @Override
    protected void initViews(View view) {
        type = getIntent().getExtras().getInt("type");
        store_id = getIntent().getExtras().getInt("store_id");

        initData(type);
        pagerAdapter = new MyAdapter(getSupportFragmentManager());
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tab_layout.setTabsFromPagerAdapter(pagerAdapter);
        listener = new TabLayout.TabLayoutOnPageChangeListener(tab_layout);
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }

    private void initData(int type) {
        tv_main_title.setText("商家主页");

        tv_shoppingCartFinish.setVisibility(View.VISIBLE);
        tv_shoppingCartFinish.setText("···");

        tv_shoppingCartFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/2/20 举报 分享
                showPopupWindow(view);
            }
        });
        if (type == 2) {
            titles.add("关于店铺");
            titles.add("藏品货架");
            titles.add("店铺发帖");
            titles.add("店铺百科");
            titles.add("店铺新闻");
            titles.add("受评区");
        } else {
            titles.add("关于他");
            titles.add("架上拍品");
            titles.add("他的帖子");
            titles.add("他的百科");
            titles.add("他的新闻");
            titles.add("受评区");
        }
    }

    private void showPopupWindow(View parent) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.pop_store_home, null);
            popupWindow = new PopupWindow(view, ScreenUtil.getInstance().getScreenWidth(this) / 5, (int) UITools.convertDpToPixel(this, 178));
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 12, 4);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = AboutHimFragment.newInstance(store_id);
                    break;
                case 1:
                    if (type == 2) {
                        fragment = HoldingShelvesFragment.newInstance(store_id);
                    } else {
                        fragment = AuctionFragment.newInstance(store_id);
                    }
                    break;
                case 2:
                    fragment = MinePostFragment.newInstance(store_id);
                    break;
                case 3:
                    fragment = EncyclopediasFragment.newInstance(store_id, 3);
                    break;
                case 4:
                    fragment = EncyclopediasFragment.newInstance(store_id, 8);
                    break;
                case 5:
                    fragment = RatedFragment.newInstance(store_id);
                    break;
                default:
                    fragment = AboutHimFragment.newInstance(store_id);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
