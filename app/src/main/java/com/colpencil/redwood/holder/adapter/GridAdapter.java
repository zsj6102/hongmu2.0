package com.colpencil.redwood.holder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.FuncPointVo;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.view.activity.cyclopedia.CycloAwardActivity;
import com.colpencil.redwood.view.activity.discovery.CustomListActivity;
import com.colpencil.redwood.view.activity.home.AnnounceActivity;
import com.colpencil.redwood.view.activity.home.CollectionCircleActivity;
import com.colpencil.redwood.view.activity.home.HelpActivity;
import com.colpencil.redwood.view.activity.home.NewListActivity;
import com.colpencil.redwood.view.activity.home.SignInActivity;
import com.colpencil.redwood.view.activity.home.WeekShootActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.AllSpecialActivity;
import com.colpencil.redwood.view.activity.mine.BrandStoreActivity;
import com.colpencil.redwood.view.activity.mine.FamousStoreActivity;
import com.colpencil.redwood.view.activity.mine.HallofFameActivity;
import com.colpencil.redwood.view.activity.mine.SpeedShotActivity;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

import static android.R.id.list;
import static com.unionpay.mobile.android.global.a.A;

public class GridAdapter extends BaseAdapter {
    public Context context;
    public List<FuncPointVo> lists;
    public int mIndex;
    public int mPagerSize;

    public GridAdapter(Context context, List<FuncPointVo> lists, int mIndex, int mPagerSize) {
        super();
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPagerSize = mPagerSize;

    }

    @Override
    public int getCount() {
        return lists.size() > (mIndex + 1) * mPagerSize ? mPagerSize : (lists.size() - mIndex * mPagerSize);
    }

    @Override
    public FuncPointVo getItem(int i) {
        return lists.get(i + mIndex * mPagerSize);
    }

    @Override
    public long getItemId(int i) {
        return i + mIndex * mPagerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_home_func, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.item_home_func_name);
            holder.iv_nul = (ImageView) convertView.findViewById(R.id.item_home_func_iv);
            holder.layout = (LinearLayout)convertView.findViewById(R.id.item_home_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPagerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        holder.tv_name.setText(lists.get(pos).getFuncname() + "");
        ImageLoaderUtils.loadImage(context, lists.get(pos).getIconurl(), holder.iv_nul);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = lists.get(pos).getFuncode();
                if (code.equals(StringConfig.MODULE_SIGNIN)) {      //签到
                    if (SharedPreferencesUtil.getInstance(context).getBoolean(StringConfig.ISLOGIN, false)) {
                        Intent intent = new Intent();
                        intent.setClass(context, SignInActivity.class);
                        context.startActivity(intent);
                    } else {
                        final CommonDialog dialog = new CommonDialog(context, "你还没登录喔!", "去登录", "取消");
                        dialog.setListener(new DialogOnClickListener() {
                            @Override
                            public void sureOnClick() {
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }

                            @Override
                            public void cancleOnClick() {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                } else if (code.equals(StringConfig.MODULE_BULLETIN)) {     //公告
                    Intent intent = new Intent();
                    intent.setClass(context, AnnounceActivity.class);
                    context.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_CIRCLE)) {     //藏友圈
                    Intent intent = new Intent();
                    intent.setClass(context, CollectionCircleActivity.class);
                    context.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_AUCTION)) {     //周拍
                    Intent intent = new Intent();
                    intent.setClass(context, WeekShootActivity.class);
                    context.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_CUSTOM)) {     //定制
                    Intent intent = new Intent();
                    intent.setClass(context, CustomListActivity.class);
                    context.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_HELP)) {     //帮助与反馈
                    Intent intent = new Intent();
                    intent.putExtra("type", "help");
                    intent.setClass(context, HelpActivity.class);
                    context.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_NEWS)) {     //新闻动态
                    Intent intent = new Intent();
                    intent.setClass(context, NewListActivity.class);
                    context.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_AWARD)) {     //百科奖励
                    Intent intent = new Intent();
                    intent.setClass(context, CycloAwardActivity.class);
                    context.startActivity(intent);
                }else if(code.equals(StringConfig.MODULE_PINPAI)){//品牌商区
                    Intent intent = new Intent();
                    intent.setClass(context, BrandStoreActivity.class);
                    context.startActivity(intent);
                }else if(code.equals(StringConfig.MODULE_SUPAI)){ //速拍商区
                    Intent intent = new Intent();
                    intent.setClass(context, SpeedShotActivity.class);
                    intent.putExtra("goFrom","gridAdapter");
                    context.startActivity(intent);
                }else if(code.equals(StringConfig.MODULE_MINSHI)){ //名师名匠
                    Intent intent = new Intent();
                    intent.setClass(context, FamousStoreActivity.class);
                    context.startActivity(intent);
                }else if(code.equals(StringConfig.MODULE_MINGRENTANG)){//名人堂
                   Intent intent = new Intent();
                    intent.setClass(context, HallofFameActivity.class);
                    context.startActivity(intent);
                }else if(code.equals(StringConfig.MODULE_ZHUANGCHANG)){//专场
                    Intent intent = new Intent();
                    intent.setClass(context, AllSpecialActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_name;
        private ImageView iv_nul;
        private LinearLayout layout;
    }
}
