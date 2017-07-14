package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BrowsingGoodDate;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DpAndPx;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class BrowsingGoodAdapter extends CommonAdapter<BrowsingGoodDate> {

    public BrowsingGoodAdapter(Context context, List<BrowsingGoodDate> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final BrowsingGoodDate item, int position) {
        holder.setText(R.id.tv_browsingDate, TimeUtil.timeFormat(item.getDate(), "MM月dd日"));
        ((SwipeListView) (holder.getView(R.id.swipeListView_browsing)))
                .setAdapter(new ItemBrowsingGoodAdapter(mContext, item.getDateList(), R.layout.item_browsinggood));
        ((SwipeListView) (holder.getView(R.id.swipeListView_browsing))).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GoodDetailActivity.class);
                intent.putExtra("goodsId", item.getDateList().get(position).getGoods_id());
                mContext.startActivity(intent);
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(mContext);
                item1.setBackground(new ColorDrawable(mContext.getResources().getColor(R.color.main_red)));
                item1.setWidth(DpAndPx.dip2px(mContext, 50));
                item1.setTitle("删除");
                item1.setTitleColor(Color.WHITE);
                item1.setTitleSize(14);
                menu.addMenuItem(item1);
            }
        };
        ((SwipeListView) holder.getView(R.id.swipeListView_browsing)).setWarpContent(true);
        ((SwipeListView) holder.getView(R.id.swipeListView_browsing)).setMenuCreator(creator);
        ((SwipeListView) holder.getView(R.id.swipeListView_browsing)).setOnMenuItemClickListener(new SwipeListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(62);
                rxBusMsg.setFoot_id(item.getDateList().get(position).getFoot_id());
                RxBus.get().post("rxBusMsg", rxBusMsg);
                return false;
            }
        });
    }
}
