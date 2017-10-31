package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.FuncPointVo;
import com.colpencil.redwood.function.widgets.MyViewPager;
import com.colpencil.redwood.function.widgets.list.Decomposers;

import com.colpencil.redwood.holder.adapter.GridAdapter;
import com.colpencil.redwood.view.adapters.ViewPagerAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;


import java.util.ArrayList;
import java.util.List;

public class ViewpagerGridViewHolder extends Decomposers<List<FuncPointVo>> {

    private ViewPager viewPager;
    private LinearLayout points;
    private ImageView[] ivPoints;
    private int totalPage; //总的页数
    private int mPageSize = 8;
    private List<View> viewPagerList = new ArrayList<>();

    public ViewpagerGridViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public void refreshView(List<FuncPointVo> funcPointVos, int position) {
        totalPage = (int) Math.ceil(funcPointVos.size() * 1.0 / mPageSize);
        for (int i = 0; i < totalPage; i++) {
            final MosaicGridView gridView = (MosaicGridView) View.inflate(mContext, R.layout.gridview_inviewpager, null);
            gridView.setAdapter(new GridAdapter(mContext, funcPointVos, i, mPageSize));
            viewPagerList.add(gridView);
        }

        viewPager.setAdapter(new ViewPagerAdapter(viewPagerList));
        ivPoints = new ImageView[totalPage];
        if (totalPage > 1) {   //页数大于1显示
            for (int i = 0; i < totalPage; i++) {
                ivPoints[i] = new ImageView(mContext);
                if (i == 0) {
                    ivPoints[i].setImageResource(R.drawable.point_select);
                } else {
                    ivPoints[i].setImageResource(R.drawable.point_unselect);
                }
                if(i == 0){
                    ivPoints[i].setPadding(0, 8, 4, 8);
                }else{
                    ivPoints[i].setPadding(4, 8, 0, 8);
                }

                points.addView(ivPoints[i]);
            }
        }


        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (totalPage > 1) {
                    for (int i = 0; i < totalPage; i++) {

                        if (i == position) {
                            ivPoints[i].setImageResource(R.drawable.point_select);
                        } else {
                            ivPoints[i].setImageResource(R.drawable.point_unselect);
                        }

                    }
                }

            }
        });
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.banner_gridview, null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        points = (LinearLayout) view.findViewById(R.id.points);
        return view;
    }

    @Override
    public View getContentView() {
        return super.getContentView();
    }


}
