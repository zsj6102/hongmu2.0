package com.colpencil.redwood.view.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;

import java.util.ArrayList;
import java.util.List;

import static com.colpencil.redwood.R.id.gridView;
import static com.colpencil.redwood.R.string.comment;


public class ItemAllAuctionAdapter extends CommonAdapter<AllGoodsInfo> {
    private MyListener listener;
    private PopupWindow popupWindow;

    public ItemAllAuctionAdapter(Context context, List<AllGoodsInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final AllGoodsInfo item, final int position) {

        ImageView iv_head = helper.getView(R.id.iv_head);
        ImageLoaderUtils.loadImage(mContext, item.getStore_logo(), iv_head);
        helper.setText(R.id.tv_shopname, item.getStore_name());
        helper.setText(R.id.tv_address, item.getStore_city());
        helper.setText(R.id.tv_shoplevel, item.getPoint_name());
        ImageView iv_shoplevel = helper.getView(R.id.iv_shoplevel);
        ImageLoaderUtils.loadImage(mContext, item.getPoint(), iv_shoplevel);

        helper.setText(R.id.tv_shoptype, item.getStore_type_name());

        ImageView iv_shoptype = helper.getView(R.id.iv_shoptype);
        ImageLoaderUtils.loadImage(mContext, item.getStore_type(), iv_shoptype);
        //        helper.setText(R.id.tv_praise,item.getGoods_rate()+"好评");
        if( item.getStore_type_name() == null || item.getStore_type_name().contains("顶藏")){
            helper.getView(R.id.tv_fans).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.tv_fans).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_fans, item.getFans_count() + "粉丝");
        }

        helper.setText(R.id.tv_introduce, "【"+item.getName()+"】"+item.getIntro());
        helper.setText(R.id.tv_creattime, item.getCreate_time());
        helper.setText(R.id.tv_collect, item.getCollectmember() + "");
        helper.setText(R.id.tv_comment, item.getComment_count() + "");
        helper.setText(R.id.tv_shopprise, "￥" + item.getPrice() + "");
        //        helper.setText(R.id.tv_shopprise,item.getStore()+"");
        if (item.getStore() <= 0) {
            helper.getView(R.id.tv_sure).setBackgroundResource(R.drawable.buy_no_left);
            helper.getView(R.id.tv_sure).setClickable(false);
            helper.setText(R.id.tv_sure, "已售罄");
        } else {
            helper.getView(R.id.tv_sure).setBackgroundResource(R.drawable.buy_red_shape);
            helper.getView(R.id.tv_sure).setClickable(true);
            helper.setText(R.id.tv_sure, "立即购买");
            //            Intent intent = new Intent(mContext, OrderActivity.   class);
            //            intent.putExtra("key", "订单确定");
            //            intent.putExtra("goFrom", "autionItem");
            //            intent.putExtra("product_id", item.getProduct_id());    //int类型
            //            intent.putExtra("goods_id", item.getGoods_id());     //int类型
            //            intent.putExtra("num", 1);    //int类型
            //            mContext.startActivity(intent);
            helper.getView(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.buy(position);
                }
            });
        }
        helper.getView(R.id.iv_mount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                //                if (popupWindow == null) {
                //                    LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //                    View view = layoutInflater.inflate(R.layout.pop_store_home, null);
                //                    popupWindow = new PopupWindow(view, ScreenUtil.getInstance().getScreenWidth(mContext) / 5, (int) UITools.convertDpToPixel(mContext, 178));
                //                }
                //                popupWindow.setFocusable(true);
                //                popupWindow.setOutsideTouchable(true);
                //                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                //                popupWindow.showAsDropDown(parent, 0, 0, Gravity.BOTTOM);
                //                showPopupWindow(view);
                View mView = LayoutInflater.from(mContext).inflate(R.layout.pop_store_home, null);

                popupWindow = new PopupWindow(mView, ScreenUtil.getInstance().getScreenWidth(mContext) / 5, (int) UITools.convertDpToPixel(mContext, 178));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, location[0] -50, location[1] +50);

            }
        });

        helper.getView(R.id.search_header_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(position);
            }
        });
        helper.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//收藏
                listener.like(position);
            }
        });
        helper.getView(R.id.reply_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.reply(position);
            }
        });
        final ImageView iv_top = helper.getView(R.id.iv_top);
        iv_top.post(new Runnable() {
            @Override
            public void run() {
                int width = ScreenUtil.getInstance().getScreenWidth(mContext);
                int height;
                if (item.getCover_proportion() != 0) {
                    height = (int) (width / item.getCover_proportion());
                    iv_top.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            }
        });
        if (item.getHave_collection() == 1) {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_like_icon);
        } else {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_unlike_icon);
        }
        if (item.getCover() == null) {
            iv_top.setVisibility(View.GONE);
        } else {
            iv_top.setVisibility(View.VISIBLE);
        }
        ImageLoaderUtils.loadImage(mContext, item.getCover(), iv_top);

        TextView tv_istop = helper.getView(R.id.tv_istop);
        if (item.getIs_top() == 1) {
            tv_istop.setVisibility(View.VISIBLE);
        } else {
            tv_istop.setVisibility(View.GONE);
        }
        RecyclerView auction_recycler = helper.getView(R.id.auction_recycler);
        auction_recycler.setHasFixedSize(true);
        HomeAllGoodsAdapter homeAllGoodsAdapter = new HomeAllGoodsAdapter(mContext, item.getList_view_gallery());
        homeAllGoodsAdapter.setListener(new HomeAllGoodsAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                ArrayList<String> imgUrls = new ArrayList<String>();
                for(int i = 0; i< item.getList_view_gallery().size();i++){
                    imgUrls.add(item.getList_view_gallery().get(i).getOriginal());
                }
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", pos);
                intent.putStringArrayListExtra("data",  imgUrls);
                mContext.startActivity(intent);
            }
        });
        auction_recycler.setAdapter( homeAllGoodsAdapter);
        if (item.getList_view_gallery().size() > 3) {
            auction_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            auction_recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }



    }

    @TargetApi(19)
    private void showPopupWindow(View parent) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.pop_store_home, null);
            popupWindow = new PopupWindow(view, ScreenUtil.getInstance().getScreenWidth(mContext) / 5, (int) UITools.convertDpToPixel(mContext, 178));
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0, Gravity.BOTTOM);
    }

    public void setListener(MyListener listener) {
        this.listener = listener;
    }

    public interface MyListener {
        void click(int position);

        void like(int position);

        void reply(int position);

        void buy(int position);
    }
}
