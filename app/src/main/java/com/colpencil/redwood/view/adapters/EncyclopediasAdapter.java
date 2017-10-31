package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.tools.RelativeDataFormat;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.finalteam.toolsfinal.DateUtils.date;
import static com.colpencil.redwood.R.id.iv_cyclopedia;
import static com.colpencil.redwood.R.id.post_time;


public class EncyclopediasAdapter extends CommonAdapter<ArticalItem> {
    private List<ArticalItem> mDatas;
    private Context context;
    public EncyclopediasAdapter(Context context, List<ArticalItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.mDatas=mDatas;
        this.context=context;
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
    public void convert(final CommonViewHolder helper, ArticalItem item, int position) {
        ImageLoaderUtils.loadImage(context, mDatas.get(position).getH_img(), (ImageView)helper.getView(R.id.encyclopedias_imageview));
        helper.setText(R.id.tv_comment_count,item.getCon_count()+"评论");
        helper.setText(R.id.tv_title,item.getH_title());
        helper.setText(R.id.tv_share_count,item.getShare_count()+"分享");
//        Date dateNow = strToDate(item.getNow_time());
//        Date date = strToDate(item.getCreate_time());
//        String diff = RelativeDataFormat.format(dateNow,date);

        helper.setText(R.id.tv_timedif,TimeUtil.getTimeDiffDay(item.getCreate_time(),item.getNow_time()));

        if(item.getIs_top() == 0){
            helper.getView(R.id.tv_zhiding).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.tv_zhiding).setVisibility(View.VISIBLE);
        }
        if(item.getIs_digest() == 0){
            helper.getView(R.id.tv_jiajing).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.tv_jiajing).setVisibility(View.VISIBLE);
        }



    }
}
