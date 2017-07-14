package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.mine.AllAuctionFragment;
import com.colpencil.redwood.view.fragments.mine.CardWallFragment;
import com.colpencil.redwood.view.fragments.mine.SpeedFragemnt;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.colpencil.redwood.R.id.speedtabLayout;
import static com.colpencil.redwood.R.id.speedviewpager;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_alloperate
)
/**
 * @deprecated 批量管理商品
 * @author QFZ
 */
public class AllOperateActivity extends ColpencilActivity {
    @Bind(R.id.base_tab_layout)
    TabLayout base_tab_layout;
    @Bind(R.id.nvp_base)
    NoScrollViewPager nvp_base;

    private MyPageAdapter adapter;
    @Override
    protected void initViews(View view) {
        adapter=new AllOperateActivity.MyPageAdapter(getSupportFragmentManager());
//        adapter.addFragment(,"销售中");
//        adapter.addFragment(,"仓库中");
        nvp_base.setAdapter(adapter);
        nvp_base.setOffscreenPageLimit(2);
        base_tab_layout.addTab(base_tab_layout.newTab().setText("销售中"));
        base_tab_layout.addTab(base_tab_layout.newTab().setText("仓库中"));
        base_tab_layout.setupWithViewPager(nvp_base);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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
