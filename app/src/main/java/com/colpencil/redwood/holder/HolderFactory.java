package com.colpencil.redwood.holder;

import android.content.Context;

import com.colpencil.redwood.holder.home.MiddleItemViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈 宝
 * @Description:holder的工厂
 * @Email 1041121352@qq.com
 * @date 2016/11/24
 */
public class HolderFactory {

    public static Map<Integer, MiddleItemViewHolder> map = new HashMap<>();

    public static MiddleItemViewHolder createHolder(int position, Context context) {
        MiddleItemViewHolder holder;
        holder = map.get(position);
        if (holder == null) {
            if (position == 0) {
                holder = new MiddleItemViewHolder(2, context);
            } else if (position == 1) {
                holder = new MiddleItemViewHolder(3, context);
            } else if (position == 2) {
                holder = new MiddleItemViewHolder(4, context);
            } else if (position == 3) {
                holder = new MiddleItemViewHolder(5, context);
            } else {
                holder = new MiddleItemViewHolder(6, context);
            }
        }
        return holder;
    }

}
