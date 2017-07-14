package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.fragments.SpecialPastFragment;
import com.colpencil.redwood.view.fragments.mine.CardWallFragment;
import com.colpencil.redwood.view.fragments.mine.DynamicFragment;
import com.colpencil.redwood.view.fragments.mine.SpecialCollectionFragment;
import com.colpencil.redwood.view.fragments.mine.SpecialIntroduceFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**@author  QFZ
 * @deprecated  专场
 * @date 2017/3/3.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_special
)

public class SpecialActivity extends ColpencilActivity {
    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpage;

    @Bind(R.id.totop_iv)
    ImageView totop_iv;

    @Bind(R.id.iv_post)
    ImageView iv_post;
    private MyPageAdapter adapter;
    private int special_id;
    private String special_name;
    @Override
    protected void initViews(View view) {
        special_id=getIntent().getIntExtra("special_id",0);
        special_name=getIntent().getStringExtra("special_name");

        totop_iv.setImageResource(R.mipmap.totop_icon);
        iv_post.setImageResource(R.mipmap.release_icon);
        tv_main_title.setText(special_name);

        adapter=new MyPageAdapter(getSupportFragmentManager());
        adapter.addFragment(SpecialIntroduceFragment.newInstance(special_id) ,"专场介绍");
        adapter.addFragment(SpecialCollectionFragment.newInstance(special_id) ,"该场藏品");
        adapter.addFragment(CardWallFragment.newInstance(0) ,"驻场名人");

        tab_layout.addTab(tab_layout.newTab().setText("专场介绍"));
        tab_layout.addTab(tab_layout.newTab().setText("该场藏品"));
        tab_layout.addTab(tab_layout.newTab().setText("驻场名人"));

        viewpage.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpage);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    class  MyPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> mFragments=new ArrayList<>();
        private List<String> titles=new ArrayList<>();
        public MyPageAdapter(FragmentManager fm){
            super(fm);

        }
        public void addFragment(Fragment fragment, String title){
            mFragments.add(fragment);
            titles.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
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
