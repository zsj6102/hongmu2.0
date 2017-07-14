package com.colpencil.redwood.view.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.SortList;
import com.property.colpencil.colpencilandroidlibrary.Ui.AnimatedExpandableListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.MosaicGridView;

import java.util.List;

/**
 * 描述：筛选适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 09 21
 */
public class ScreenSearchAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private List<SortList> mGroupList;

    private Context mContext;

    public ScreenSearchAdapter(List<SortList> groupList, Context context) {
        this.mGroupList = groupList;
        this.mContext = context;


    }

    //父项布局内容
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_screensearch_group, null);
            viewHolder = new GroupViewHolder();
            viewHolder.tv_screenSearch_maintitle = (TextView) convertView.findViewById(R.id.tv_screenSearch_maintitle);
            viewHolder.tv_screenSearch_righttitle = (TextView) convertView.findViewById(R.id.tv_screenSearch_righttitle);
            viewHolder.iv_expand = (ImageView) convertView.findViewById(R.id.iv_expand);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.tv_screenSearch_maintitle.setText(mGroupList.get(groupPosition).getSort_list_name());
        if (mGroupList.get(groupPosition).getRightTitleName() != null && !"".equals(mGroupList.get(groupPosition).getRightTitleName())) {
            boolean isChoose = false;
            for (int i = 0; i < mGroupList.get(groupPosition).getSort_list().size(); i++) {
                if (mGroupList.get(groupPosition).getSort_list().get(i).isChooseState()) {
                    isChoose = true;
                    break;
                }
            }
            if (isChoose) {
                viewHolder.tv_screenSearch_righttitle.setText(mGroupList.get(groupPosition).getRightTitleName());
            } else {
                viewHolder.tv_screenSearch_righttitle.setText("");
            }
        } else {
            viewHolder.tv_screenSearch_righttitle.setText("");
        }
        return convertView;
    }

    //子项布局内容
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_screensearch_child, null);
            viewHolder = new ChildViewHolder();
            viewHolder.item_gv_screenSearch = (MosaicGridView) convertView.findViewById(R.id.item_gv_screenSearch);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        viewHolder.item_gv_screenSearch.setAdapter(new ScreenSearchGVAdapter(mContext, mGroupList.get(groupPosition).getSort_list(), R.layout.item_gv_screensearch, groupPosition));
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupList.get(groupPosition).getSort_list();
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
        return true;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 父布局ViewHolder
     */
    public class GroupViewHolder {
        public TextView tv_screenSearch_maintitle;
        public TextView tv_screenSearch_righttitle;
        public ImageView iv_expand;
    }

    /**
     * 子布局
     */
    public class ChildViewHolder {
        MosaicGridView item_gv_screenSearch;
    }
}

