package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

public class HomeMiddleGoodAdapter extends RecyclerView.Adapter<HomeMiddleGoodAdapter.MyViewHolder> {

    private Context context;
    private List<HomeGoodInfo> mDatas;
    private int width;
    private int height;

    public HomeMiddleGoodAdapter(Context context, List<HomeGoodInfo> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_today_recommend, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoaderUtils.loadImage(context, mDatas.get(position).getImage(), holder.iv);
        holder.tv_name.setText(mDatas.get(position).getGoodsName());
        holder.tv_price.setText("￥" + FormatUtils.formatDouble(mDatas.get(position).getCostPrice()));
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodDetailActivity.class);
                intent.putExtra("goodsId", mDatas.get(position).getGoodsId());
                context.startActivity(intent);
            }
        });
        width = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("goodwidth", 0);
        height = SharedPreferencesUtil.getInstance(App.getInstance()).getInt("goodheight", 0);
        if (width == 0 || height == 0) {
            holder.iv.post(new Runnable() {
                @Override
                public void run() {
                    width = holder.iv.getWidth();
                    height = width;
                    SharedPreferencesUtil.getInstance(App.getInstance()).setInt("goodwidth", width);
                    SharedPreferencesUtil.getInstance(App.getInstance()).setInt("goodheight", height);
                    holder.iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            holder.iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv_name;
        private TextView tv_price;
        private LinearLayout ll_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.today_good_iv);
            tv_name = (TextView) itemView.findViewById(R.id.item_good_name);
            tv_price = (TextView) itemView.findViewById(R.id.item_good_price);
            ll_content = (LinearLayout) itemView.findViewById(R.id.item_home_regood_ll);
        }
    }
}
