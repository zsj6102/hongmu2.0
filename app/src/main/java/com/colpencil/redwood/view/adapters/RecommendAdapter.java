package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

/**
 * @author 陈宝
 * @Description:商品推荐适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {

    private Context context;
    private List<HomeGoodInfo> list;
    /**
     * 控件宽
     */
    private int width;
    /**
     * 控件高
     */
    private int height;

    public RecommendAdapter(Context context, List<HomeGoodInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_good, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoaderUtils.loadImage(context, list.get(position).getImage(), holder.iv);
        holder.tv.setText(list.get(position).getGoodsname());
        holder.price.setText("￥" + list.get(position).getCostprice());
        if (width == 0 || height == 0) {
            holder.iv.post(new Runnable() {//动态更换ImageView的高度
                @Override
                public void run() {
                    width = holder.iv.getWidth();
                    height = width;
                    holder.iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                }
            });
        } else {
            holder.iv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        }
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodBusMsg msg = new GoodBusMsg();
                msg.setGoodsId(list.get(position).getGoodsid());
                msg.setType("Reload");
                RxBus.get().post(StringConfig.GOODSBUS, msg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv;
        TextView price;
        LinearLayout ll_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_recommend_goodimg);
            tv = (TextView) itemView.findViewById(R.id.item_recommend_goodname);
            ll_content = (LinearLayout) itemView.findViewById(R.id.ll_recommend);
            price = (TextView) itemView.findViewById(R.id.item_recommend_goodprice);
        }
    }
}
