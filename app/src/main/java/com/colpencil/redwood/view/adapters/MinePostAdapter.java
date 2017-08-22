package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MinePostInfo;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.function.tools.RelativeDataFormat;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.R.attr.format;
import static cn.finalteam.toolsfinal.DateUtils.date;
import static com.colpencil.redwood.R.id.minepost_recycler;

public class MinePostAdapter extends CommonAdapter<MinePostItem> {

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
    public void convert(CommonViewHolder helper, MinePostItem item, int position) {
        RecyclerView recycler=helper.getView(minepost_recycler);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new PostItemAdapter(mContext, mDatas.get(position).getImg_list()));
        if (mDatas.get(position).getImg_list().size() > 3) {
            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }
        helper.setText(R.id.tv_title,item.getOte_title());
        helper.setText(R.id.tv_content,item.getOte_content());
        Date dateNow = strToDate(item.getNowtime());
        Date date = strToDate(item.getCreate_time());
        String diff = RelativeDataFormat.format(dateNow,date);
        helper.setText(R.id.tv_timedif, diff);
        helper.setText(R.id.tv_like_num,item.getLike_count()+"");
        helper.setText(R.id.tv_comment_count,item.getCon_count()+"");
        helper.setText(R.id.tv_share_count,item.getShare_count()+"");
    }
}
