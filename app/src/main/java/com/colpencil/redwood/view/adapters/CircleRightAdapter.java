package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MyComment;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.home.CommentDetailActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈宝
 * @Description:圈子列表的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CircleRightAdapter extends CommonAdapter<MyComment> implements OnClickListener {

    private Context context;
    private ComOnClickListener listener;

    public CircleRightAdapter(Context context, List<MyComment> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder holder, final MyComment comment, final int position) {
        holder.getView(R.id.remove_iv).setVisibility(View.VISIBLE);
        holder.setText(R.id.circle_left_item_title, comment.getOte_title());
        holder.setText(R.id.circle_left_item_nickname, comment.getNickname());
        holder.setText(R.id.circle_left_item_content, comment.getOte_content());
        holder.setText(R.id.circle_left_item_likenum, comment.getLike_count() + "");
        holder.setText(R.id.circle_left_item_commentnum, comment.getCom_count() + "");
        holder.setText(R.id.circle_left_item_sharenum, comment.getOte_sharenum() + "");
        holder.setText(R.id.circle_left_item_time, TimeUtil.getTimeDiffDay(comment.getCreatetime(), System.currentTimeMillis()));
        holder.setImageByUrl(context, R.id.user_level_iv, comment.getMember_photo());
        ImageLoaderUtils.loadImage(mContext, comment.getFace(), (SelectableRoundedImageView) holder.getView(R.id.circle_left_item_head));
        holder.getView(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentDetailActivity.class);
                intent.putExtra("commentid", comment.getOte_id());
                context.startActivity(intent);
            }
        });
        holder.getView(R.id.remove_iv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.removeComments(position);
            }
        });
        holder.getView(R.id.btn_comment).setTag(position);
        holder.getView(R.id.btn_comment).setOnClickListener(this);
        MosaicGridView gridView = holder.getView(R.id.circle_left_item_gridview);
        CircleImageAdapter adapter;
        if (!ListUtils.listIsNullOrEmpty(comment.getOte_img())) {
            adapter = new CircleImageAdapter(context, comment.getOte_img(), R.layout.item_circle_image);
        } else {
            adapter = new CircleImageAdapter(context, new ArrayList<String>(), R.layout.item_circle_image);
        }
        gridView.setAdapter(adapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) comment.getOriginal_img());
                context.startActivity(intent);
            }
        });
        if (comment.getOte_digest() == 0) {
            holder.getView(R.id.add_plus).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.add_plus).setVisibility(View.VISIBLE);
        }
        if (comment.getOte_stick() == 0) {
            holder.getView(R.id.iv_sticky).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.iv_sticky).setVisibility(View.VISIBLE);
        }
        holder.getView(R.id.ll_like).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.like(position);
            }
        });
        if (comment.getLike() == 1) {
            holder.setImageById(R.id.circle_left_like, R.mipmap.iv_like_icon);
        } else {
            holder.setImageById(R.id.circle_left_like, R.mipmap.circle_left_like);
        }
        holder.getView(R.id.item_circle_share).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.share(position);
            }
        });
        holder.getView(R.id.rl_content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("key", "info");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int position = (int) v.getTag();
            listener.itemClicks(position);
        }
    }

    public void setListener(ComOnClickListener listener) {
        this.listener = listener;
    }

    public interface ComOnClickListener {
        void itemClicks(int position);

        void removeComments(int position);

        void like(int position);

        void share(int position);
    }
}
