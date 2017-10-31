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
import com.colpencil.redwood.bean.AllGoodsImgInfo;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

import static com.colpencil.redwood.R.id.today_good_iv;

public class HomeAllGoodsAdapter extends RecyclerView.Adapter<HomeAllGoodsAdapter.MyViewHolder> {

    private Context context;
    private List<AllGoodsImgInfo> mDatas;
    private int width;
    private int height;
    private MyItemClickListener listener;
    public HomeAllGoodsAdapter(Context context, List<AllGoodsImgInfo> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_allgoods, null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoaderUtils.loadImage(context, mDatas.get(position).getThumbnail(), holder.iv);

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

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setListener(MyItemClickListener listener){
        this.listener = listener;
    }
    public interface MyItemClickListener {
         void onItemClick(int postion);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
//        private View leftView;
//        private View rightView;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.today_good_iv);
//            leftView = (View)itemView.findViewById()
        }
    }
}
