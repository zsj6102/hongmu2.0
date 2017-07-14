package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BrowsingCyclopediaDate;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.function.widgets.swipe.SwipeListView;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenu;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuCreator;
import com.colpencil.redwood.function.widgets.swipe.SwipeMenuItem;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.DpAndPx;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import java.util.List;

;
;
;

/**
 * @author 曾 凤
 * @Description: 商品
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class BrowsingCyclopediaAdapter extends CommonAdapter<BrowsingCyclopediaDate> {

    public BrowsingCyclopediaAdapter(Context context, List<BrowsingCyclopediaDate> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final BrowsingCyclopediaDate mdate, int position) {
        helper.setText(R.id.tv_browsingDate, TimeUtil.timeFormat(mdate.getDate(), "MM月dd日"));
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setWarpContent(true);
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing))
                .setAdapter(new ItemBrowsingCyclopediaAdapter(mContext, mdate.getDateList(), R.layout.item_browsingcyclopedia));
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, CyclopediaDetailActivity.class);
                CycloParams params = new CycloParams();
                params.article_id = mdate.getDateList().get(position).getArticle_id() + "";
                params.type = "cyclopedia";
                params.title = mdate.getDateList().get(position).getTitle();
                params.content = mdate.getDateList().get(position).getPage_description();
                params.image = mdate.getDateList().get(position).getImage();
                params.time = mdate.getDateList().get(position).getCreatime();
                intent.putExtra("params", params);
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
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setMenuCreator(creator);
        ((SwipeListView) helper.getView(R.id.swipeListView_browsing)).setOnMenuItemClickListener(new SwipeListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(60);
                rxBusMsg.setFoot_id(mdate.getDateList().get(position).getFoot_id());
                RxBus.get().post("rxBusMsg", rxBusMsg);
                return false;
            }
        });
    }

}
