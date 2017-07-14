package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BrowsingPostDate;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.view.activity.home.CommentDetailActivity;
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
public class BrowsingPostAdapter extends CommonAdapter<BrowsingPostDate> {

    public BrowsingPostAdapter(Context context, List<BrowsingPostDate> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final BrowsingPostDate browsingPostDate, int position) {
        helper.setText(R.id.tv_browsingDate, TimeUtil.timeFormat(browsingPostDate.getDate(), "MM月dd日"));
        ItemBrowsingPostAdapter mAdapter = new ItemBrowsingPostAdapter(mContext, browsingPostDate.getDateList(), R.layout.item_browsingcyclopedia);
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setAdapter(mAdapter);
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, CommentDetailActivity.class);
                intent.putExtra("commentid", browsingPostDate.getDateList().get(position).getOte_id());
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
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setWarpContent(true);
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setMenuCreator(creator);
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setOnMenuItemClickListener(new SwipeListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(61);
                rxBusMsg.setFoot_id(browsingPostDate.getDateList().get(position).getFoot_id());
                RxBus.get().post("rxBusMsg", rxBusMsg);
                return false;
            }
        });
    }

}
