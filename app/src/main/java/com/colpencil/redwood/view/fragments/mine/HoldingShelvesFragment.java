package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_shelves
)
public class HoldingShelvesFragment extends ColpencilFragment implements View.OnClickListener {

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    @Bind(R.id.tv_normal)
    TextView tv_normal;

    @Bind(R.id.tv_speed)
    TextView tv_speed;

    @Bind(R.id.tv_week)
    TextView tv_week;

    private MyAdapter myAdapter;

    @Override
    protected void initViews(View view) {
        myAdapter = new MyAdapter(getChildFragmentManager());
        viewpager.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        tv_normal.setOnClickListener(this);
        tv_speed.setOnClickListener(this);
        tv_week.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_normal:
                tv_normal.setTextColor(getResources().getColor(R.color.main_red));
                tv_speed.setTextColor(getResources().getColor(R.color.text_color_first));
                tv_week.setTextColor(getResources().getColor(R.color.text_color_first));
                viewpager.setCurrentItem(0,false);
                break;
            case R.id.tv_speed:
                tv_normal.setTextColor(getResources().getColor(R.color.text_color_first));
                tv_speed.setTextColor(getResources().getColor(R.color.main_red));
                tv_week.setTextColor(getResources().getColor(R.color.text_color_first));
                viewpager.setCurrentItem(1,false);
                break;
            case R.id.tv_week:
                tv_normal.setTextColor(getResources().getColor(R.color.text_color_first));
                tv_speed.setTextColor(getResources().getColor(R.color.text_color_first));
                tv_week.setTextColor(getResources().getColor(R.color.main_red));
                viewpager.setCurrentItem(2,false);
                break;
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new WeekShelfFragment();
                    break;
                case 1:
                    fragment = new AboutHimFragment();
                    break;
                case 2:
                    fragment = new AboutHimFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}