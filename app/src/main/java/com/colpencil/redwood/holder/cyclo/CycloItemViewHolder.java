package com.colpencil.redwood.holder.cyclo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

public class CycloItemViewHolder extends Decomposers<CyclopediaInfoVo> {


    private TextView title;
    private ImageView iv_cyclopedia;
    private SelectableRoundedImageView iv_head;
    private TextView tv_username;
    private TextView comment_num;
    private TextView share_num;
    private TextView post_time;
    private View line_view;
    private TextView iv_sticky;
    private TextView add_plus;

    public CycloItemViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_second_cyclopedia, null);
        title = (TextView) view.findViewById(R.id.cyclopedia_title);
        iv_cyclopedia = (ImageView) view.findViewById(R.id.iv_cyclopedia);
        iv_head = (SelectableRoundedImageView) view.findViewById(R.id.iv_head);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        comment_num = (TextView) view.findViewById(R.id.comment_num);
        share_num = (TextView) view.findViewById(R.id.share_num);
        post_time = (TextView) view.findViewById(R.id.post_time);
        line_view = view.findViewById(R.id.line_view);
        iv_sticky = (TextView) view.findViewById(R.id.iv_sticky);
        add_plus = (TextView) view.findViewById(R.id.add_plus);
        return view;
    }

    @Override
    public void refreshView(final CyclopediaInfoVo infoVo, int position) {
        title.setText(infoVo.getPage_title());
        ImageLoaderUtils.loadImage(mContext, infoVo.getImage(), iv_cyclopedia);
        ImageLoaderUtils.loadImage(mContext, infoVo.getAuthor_face(), iv_head);
        tv_username.setText(infoVo.getAuthor_nickname());
        comment_num.setText(infoVo.getComment_count() + "评论");
        share_num.setText(infoVo.getShare_count() + "分享");
        post_time.setText(TimeUtil.getTimeDiffDay(infoVo.getAdd_time(), infoVo.getCurrent_time()));
        if (position == 0) {
            line_view.setVisibility(View.GONE);
        } else {
            line_view.setVisibility(View.VISIBLE);
        }
        //置顶
        if (infoVo.getIs_top() == 0) {
            iv_sticky.setVisibility(View.GONE);
        } else {
            iv_sticky.setVisibility(View.VISIBLE);
        }
        //加精
        if (infoVo.getIs_splendid() == 0) {
            add_plus.setVisibility(View.GONE);
        } else {
            add_plus.setVisibility(View.VISIBLE);
        }
        iv_cyclopedia.post(new Runnable() {
            @Override
            public void run() {
                int width = ScreenUtil.getInstance().getScreenWidth(mContext);
                int height = (int) (width / infoVo.getImage_scale());
                iv_cyclopedia.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            }
        });
    }
}
