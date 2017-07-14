package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.SpecialPastItem;
import com.property.colpencil.colpencilandroidlibrary.Function.ColpencilLogger.ColpencilLogger;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SpecialPastAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<SpecialPastItem> pastItemList;

    public SpecialPastAdapter(Context context, List<SpecialPastItem> pastItems){
        this.mContext = context;
        this.pastItemList = pastItems;
    }

    @Override
    public int getGroupCount() {
        return pastItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return pastItemList.get(groupPosition).getImgUrl().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return pastItemList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return pastItemList.get(groupPosition).getImgUrl().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if(convertView == null){
            groupViewHolder = new GroupViewHolder();
            convertView = View.inflate(mContext, R.layout.item_special_past_group, null);
            groupViewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        convertView.setPadding(0,15,0,0);
        groupViewHolder.tv_time.setText(pastItemList.get(groupPosition).getTime());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if(convertView == null){
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(mContext, R.layout.item_special_past_child, null);
            childViewHolder.iv_special = (ImageView) convertView.findViewById(R.id.iv_special);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        ImgTool.getImgToolInstance(mContext).loadImgByString(pastItemList.get(groupPosition).getImgUrl().get(childPosition), childViewHolder.iv_special);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    private class GroupViewHolder{
        TextView tv_time;
    }

    private class ChildViewHolder{
        ImageView iv_special;
    }
}
