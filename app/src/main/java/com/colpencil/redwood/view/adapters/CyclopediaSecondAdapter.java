package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.List;

/**
 * @author 陈宝
 * @Description:百科第二个ListView的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
public class CyclopediaSecondAdapter extends CommonAdapter<CyclopediaInfoVo> {

    private Context context;

    public CyclopediaSecondAdapter(Context context, List<CyclopediaInfoVo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(final CommonViewHolder holder, final CyclopediaInfoVo item, int position) {
        holder.setText(R.id.cyclopedia_title, item.getPage_title());
        holder.setImageByUrl(mContext, R.id.iv_cyclopedia, item.getImage());
        ImageLoaderUtils.loadImage(mContext, item.getAuthor_face(), (SelectableRoundedImageView) holder.getView(R.id.iv_head));
        holder.setText(R.id.tv_username, item.getAuthor_nickname());
        holder.setText(R.id.comment_num, item.getComment_count() + "评论");
        holder.setText(R.id.share_num, item.getShare_count() + "分享");
        holder.setText(R.id.post_time, TimeUtil.getTimeDiffDay(item.getAdd_time(), item.getCurrent_time()));
        if (position == 0) {
            holder.getView(R.id.line_view).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.line_view).setVisibility(View.VISIBLE);
        }
        //置顶
        if (item.getIs_top() == 0) {
            holder.getView(R.id.iv_sticky).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.iv_sticky).setVisibility(View.VISIBLE);
        }
        //加精
        if (item.getIs_splendid() == 0) {
            holder.getView(R.id.add_plus).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.add_plus).setVisibility(View.VISIBLE);
        }
        holder.getView(R.id.iv_cyclopedia).post(new Runnable() {
            @Override
            public void run() {
                int width = ScreenUtil.getInstance().getScreenWidth(context);
                int height = (int) (width / item.getImage_scale());
                holder.getView(R.id.iv_cyclopedia).setLayoutParams(new LinearLayout.LayoutParams(width, height));
            }
        });
    }

}
