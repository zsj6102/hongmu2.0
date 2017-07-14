package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

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
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.List;

/**
 * @author 陈宝
 * @Description:功能点的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class FuncItemAdapter extends CommonAdapter<FuncPointVo> {

    public FuncItemAdapter(Context context, List<FuncPointVo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final FuncPointVo item, int position) {
        ImageLoaderUtils.loadImage(mContext, item.getIconurl(), (ImageView) holder.getView(R.id.item_home_func_iv));
        holder.setText(R.id.item_home_func_name, item.getFuncname());
        holder.getView(R.id.item_home_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = item.getFuncode();
                if (code.equals(StringConfig.MODULE_SIGNIN)) {      //签到
                    if (SharedPreferencesUtil.getInstance(mContext).getBoolean(StringConfig.ISLOGIN, false)) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, SignInActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        final CommonDialog dialog = new CommonDialog(mContext, "你还没登录喔!", "去登录", "取消");
                        dialog.setListener(new DialogOnClickListener() {
                            @Override
                            public void sureOnClick() {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                                mContext.startActivity(intent);
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
                    intent.setClass(mContext, AnnounceActivity.class);
                    mContext.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_CIRCLE)) {     //藏友圈
                    Intent intent = new Intent();
                    intent.setClass(mContext, CollectionCircleActivity.class);
                    mContext.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_AUCTION)) {     //周拍
                    Intent intent = new Intent();
                    intent.setClass(mContext, WeekShootActivity.class);
                    mContext.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_CUSTOM)) {     //定制
                    Intent intent = new Intent();
                    intent.setClass(mContext, CustomListActivity.class);
                    mContext.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_HELP)) {     //帮助与反馈
                    Intent intent = new Intent();
                    intent.putExtra("type", "help");
                    intent.setClass(mContext, HelpActivity.class);
                    mContext.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_NEWS)) {     //新闻动态
                    Intent intent = new Intent();
                    intent.setClass(mContext, NewListActivity.class);
                    mContext.startActivity(intent);
                } else if (code.equals(StringConfig.MODULE_AWARD)) {     //百科奖励
                    Intent intent = new Intent();
                    intent.setClass(mContext, CycloAwardActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
