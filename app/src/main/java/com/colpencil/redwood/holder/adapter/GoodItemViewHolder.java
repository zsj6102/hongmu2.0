package com.colpencil.redwood.holder.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.adapters.ViewPagerAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.ArrayList;
import java.util.List;

public class GoodItemViewHolder  extends Decomposers<Goods_list> {

    private MosaicGridView recycler;
    private ViewPager viewPager;
    private List<View> viewPagerList = new ArrayList<>();

    public GoodItemViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.good_recommend_foryou, null);
        viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        recycler = (MosaicGridView) itemView.findViewById(R.id.home_good_recycler);
        return itemView;
    }

    @Override
    public void refreshView(final Goods_list vo, int position) {
        if(vo.getGoods_list()!=null){
            recycler.setVisibility(View.VISIBLE);
            recycler.setAdapter(new HGlistAdapter(mContext, vo.getGoods_list(),R.layout.item_home_good));
        }

        if (!ListUtils.listIsNullOrEmpty(vo.getAdv_list())) {
            viewPager.setVisibility(View.VISIBLE);
            viewPagerList.clear();
            for (int i = 0; i < vo.getAdv_list().size(); i++) {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoaderUtils.loadImage(mContext, vo.getAdv_list().get(0).getUrl(), imageView);
                viewPagerList.add(imageView);
            }
            viewPager.setAdapter(new ViewPagerAdapter(viewPagerList));
        }

        ViewConfiguration configuration = ViewConfiguration.get(mContext);
        final float mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int touchFlag = 0;
            float x = 0, y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchFlag = 0;
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float xDiff = Math.abs(event.getX() - x);
                        float yDiff = Math.abs(event.getY() - y);
                        if (xDiff < mTouchSlop && xDiff >= yDiff)
                            touchFlag = 0;
                        else
                            touchFlag = -1;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (touchFlag == 0) {
                            if (!ListUtils.listIsNullOrEmpty(vo.getAdv_list())) {
                                int currentItem = viewPager.getCurrentItem();
                                BannerVo bannerVo = vo.getAdv_list().get(currentItem);
                                if ("good".equals(bannerVo.getType())) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, GoodDetailActivity.class);
                                    intent.putExtra("goodsId", bannerVo.getGoodsId());
                                    mContext.startActivity(intent);
                                } else if ("link".equals(bannerVo.getType())) {
                                    Intent intent = new Intent();
                                    intent.setClass(mContext, MyWebViewActivity.class);
                                    intent.putExtra("webviewurl", bannerVo.getHtmlurl());
                                    intent.putExtra("type", "banner");
                                    mContext.startActivity(intent);
                                }
                            }

                        }
                        break;
                }
                return false;
            }
        });

        }

}
