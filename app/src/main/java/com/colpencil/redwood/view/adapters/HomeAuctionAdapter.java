package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.HomeAuctionInfo;
import com.colpencil.redwood.bean.UrlString;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

import static android.R.attr.width;

public class HomeAuctionAdapter extends RecyclerView.Adapter<HomeAuctionAdapter.MyViewHolder> {
    private Context context;
    private List<UrlString> mdata;
    private int width;
    private int height;
    private  MyItemClickListener listener;
    public  HomeAuctionAdapter(Context context, List<UrlString> mdata){
        this.context=context;
        this.mdata=mdata;

    }
    @Override
    public HomeAuctionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_auction, null));
    }

    @Override
    public void onBindViewHolder(final HomeAuctionAdapter.MyViewHolder holder, final int position) {
        ImageLoaderUtils.loadImage(context, mdata.get(position).getThumbnail(), holder.iv);

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
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }
    public void setListener( MyItemClickListener listener){
        this.listener = listener;
    }
    public interface MyItemClickListener {
        void onItemClick(int postion);
    }
    @Override
    public int getItemCount() {
        return mdata.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private LinearLayout ll_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.auction_iv);
            ll_content = (LinearLayout) itemView.findViewById(R.id.item_home_auction_ll);
        }
    }
}
