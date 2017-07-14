package com.colpencil.redwood.function.widgets.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    public TextView delete;
    public TextView cancle;
    public ImageView ivIcon;
    public TextView title;
    public TextView content;
    public LinearLayout layout;

    public ListViewHolder(View itemView) {
        super(itemView);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        cancle = (TextView) itemView.findViewById(R.id.item_cancle);
        ivIcon = (ImageView) itemView.findViewById(R.id.item_cyclopedia_iv);
        title = (TextView) itemView.findViewById(R.id.item_cyclopedia_title);
        content = (TextView) itemView.findViewById(R.id.item_cyclopedia_content);
        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
