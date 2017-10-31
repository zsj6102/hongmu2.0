package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.function.tools.RelativeDataFormat;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.home.CommentDetailActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MinePostAdapter extends CommonAdapter<MinePostItem>   {
    private  ComOnClickListener listener;
    public MinePostAdapter(Context context, List<MinePostItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }
    public static Date strToDate(String str)   {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    @Override
    public void convert(CommonViewHolder helper, final  MinePostItem item, final int position) {
//        RecyclerView recycler=helper.getView(minepost_recycler);
//        recycler.setHasFixedSize(true);
//        recycler.setAdapter(new PostItemAdapter(mContext, mDatas.get(position).getImg_list()));
//        if (mDatas.get(position).getImg_list().size() > 3) {
//            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//        } else {
//            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
//        }
        MosaicGridView gridView = helper.getView(R.id.circle_left_item_gridview);
        CircleImageAdapter adapter;
        if (!ListUtils.listIsNullOrEmpty(item.getImg_list())) {
            adapter = new CircleImageAdapter(mContext, item.getImg_list(), R.layout.item_circle_image);
        } else {
            adapter = new CircleImageAdapter(mContext, new ArrayList<String>(), R.layout.item_circle_image);
        }
        helper.getView(R.id.item_minepost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentDetailActivity.class);
                intent.putExtra("commentid", item.getOte_id());
                mContext.startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) item.getImg_list());
                mContext.startActivity(intent);
            }
        });
        helper.setText(R.id.tv_title,item.getOte_title());
        helper.setText(R.id.tv_content,item.getOte_content());
        Date dateNow = strToDate(item.getNowtime());
        Date date = strToDate(item.getCreate_time());
        String diff = RelativeDataFormat.format(dateNow,date);
        helper.setText(R.id.tv_timedif, diff);
        helper.setText(R.id.tv_like_num,item.getLike_count()+"");
        helper.setText(R.id.tv_comment_count,item.getCon_count()+"");
        helper.setText(R.id.tv_share_count,item.getShare_count()+"");
        if(item.getIslike() == 1){
            helper.setImageById(R.id.iv_like,R.mipmap.iv_like_icon);
        }else{
            helper.setImageById(R.id.iv_like,R.mipmap.iv_unlike_icon);
        }
        helper.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.likeClick(position);
            }
        });
        helper.getView(R.id.share_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.shareClick(position);
            }
        });
        helper.getView(R.id.search_header_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemClicks(position);
            }
        });
    }



    public void setListener(ComOnClickListener listener) {
        this.listener = listener;
    }
    public interface ComOnClickListener {
        void itemClicks(int position);

        void likeClick(int position);

        void shareClick(int position);
    }
}
