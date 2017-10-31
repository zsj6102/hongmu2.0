package com.colpencil.redwood.view.activity.mine;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.CollectionPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.fragments.mine.AboutHimFragment;
import com.colpencil.redwood.view.fragments.mine.AuctionFragment;
import com.colpencil.redwood.view.fragments.mine.EncyclopediasFragment;
import com.colpencil.redwood.view.fragments.mine.HoldingShelvesFragment;
import com.colpencil.redwood.view.fragments.mine.MinePostFragment;
import com.colpencil.redwood.view.fragments.mine.RatedFragment;
import com.colpencil.redwood.view.impl.ICollectionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ScreenUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.UITools;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author D * S
 * @date 2017/2/17
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_store_home)
public class StoreHomeActivity extends ColpencilActivity implements ICollectionView {


    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_shoppingCartFinish;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;
    private List<String> titles = new ArrayList<>();
    private int type; // 0 商家主页   1 个人主页
    private int store_id; //商家或个人的id
    private MyAdapter pagerAdapter;
    private TabLayout.TabLayoutOnPageChangeListener listener;
    private CollectionPresenter presenter;
    private PopupWindow popupWindow;
    private PopupWindow window;
    private String title;
    private String content;
    private int ote_id;
    private String images;
    private View mView;
    private UMImage image;
    private ShareAction action;

    @Override
    protected void initViews(View view) {
        type = getIntent().getExtras().getInt("type");
        store_id = getIntent().getExtras().getInt("store_id");
        mView = view;
        initData(type);
        pagerAdapter = new MyAdapter(getSupportFragmentManager());
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tab_layout.setTabsFromPagerAdapter(pagerAdapter);
        listener = new TabLayout.TabLayoutOnPageChangeListener(tab_layout);
        viewpager.addOnPageChangeListener(listener);
        viewpager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
        action = new ShareAction(this);
        initBus();
    }

    private void initBus() {
        observable = RxBus.get().register("refresh", RefreshMsg.class);
        subscriber = new Subscriber<RefreshMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg msg) {
                if (msg.getType() == 11) {
                    title = msg.getTitle();
                    content = msg.getContent();
                    ote_id = msg.getId();
                    images = msg.getImage();
                    presenter.share(ote_id);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void initData(int type) {
        tv_main_title.setText("商家主页");

        tv_shoppingCartFinish.setVisibility(View.VISIBLE);
        tv_shoppingCartFinish.setText("···");

        tv_shoppingCartFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/2/20 举报 分享
                showPopupWindow(view);
            }
        });
        if (type == 2 ) {
            titles.add("关于店铺");
            titles.add("藏品货架");
            titles.add("店铺发帖");
            titles.add("店铺百科");
            titles.add("店铺新闻");
            titles.add("受评区");
        } else {
            titles.add("关于他");
            if(type==3){
                titles.add("代表作");
            }else{
                titles.add("架上拍品");
            }

            titles.add("他的帖子");
            titles.add("他的百科");
            titles.add("他的新闻");
            titles.add("受评区");
        }
    }

    @TargetApi(19)
    private void showPopupWindow(View parent) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.pop_store_home, null);
            popupWindow = new PopupWindow(view, ScreenUtil.getInstance().getScreenWidth(this) / 5, (int) UITools.convertDpToPixel(this, 178));
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, -15, 10, Gravity.BOTTOM);
    }

    @Override
    public void shareResult(CommonResult result) {
        if (result.getCode().equals("3")) {

            showDialog();
        } else if (result.getCode().equals("1")) {
            showPopupWindow2(result.getUrl());
        }
    }

    private void showPopupWindow2(String shareUrl) {
        if (window == null) {
            window = new PopupWindow(initPop(shareUrl), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(mView, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(mView);
    }

    private View initPop(final String shareUrl) {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_share, null);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSharePop();
            }
        });
        view.findViewById(R.id.ll_share_qq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QQ, shareUrl);
            }
        });
        view.findViewById(R.id.ll_share_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.SINA, shareUrl);
            }
        });
        view.findViewById(R.id.ll_share_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN, shareUrl);
            }
        });
        view.findViewById(R.id.ll_share_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN_CIRCLE, shareUrl);
            }
        });
        return view;
    }

    private void dismissSharePop() {
        if (window != null) {
            window.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            RefreshMsg msg = new RefreshMsg();
            msg.setType(3);
            RxBus.get().post("refreshmsg", msg);
            presenter.share(ote_id);
        }
    }

    private void share(SHARE_MEDIA platform, String shareUrl) {
        if (TextUtils.isEmpty(images)) {
            image = new UMImage(this, BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        } else {
            image = new UMImage(this, images);
        }
        action.setPlatform(platform).setCallback(umShareListener).withTitle(title).withText(content).withTargetUrl(shareUrl).withMedia(image).share();
        dismissSharePop();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastTools.showShort(StoreHomeActivity.this, "分享成功");
            if (share_media == SHARE_MEDIA.QQ) {  //QQ
                presenter.recordShare(ote_id, "qq");
            } else if (share_media == SHARE_MEDIA.WEIXIN) {   //微信
                presenter.recordShare(ote_id, "friend");
            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {    //朋友圈
                presenter.recordShare(ote_id, "weixin");
            } else {    //新浪
                presenter.recordShare(ote_id, "xinlang");
            }
            dismissSharePop();
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastTools.showShort(StoreHomeActivity.this, "分享失败");
            dismissSharePop();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastTools.showShort(StoreHomeActivity.this, "分享取消");
            dismissSharePop();
        }
    };

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(StoreHomeActivity.this, LoginActivity.class);
                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                startActivityForResult(intent, Constants.REQUEST_LOGIN);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CollectionPresenter();
        return presenter;
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = AboutHimFragment.newInstance(store_id,type);
                    break;
                case 1:
                    if (type == 2 || type == 3) {
                        fragment = HoldingShelvesFragment.newInstance(store_id,type);
                    } else {
                        fragment = AuctionFragment.newInstance(store_id);
                    }
                    break;
                case 2:
                    fragment = MinePostFragment.newInstance(store_id);
                    break;
                case 3:
                    fragment = EncyclopediasFragment.newInstance(store_id, 3);//百科
                    break;
                case 4:
                    fragment = EncyclopediasFragment.newInstance(store_id, 8);//新闻
                    break;
                case 5:
                    fragment = RatedFragment.newInstance(store_id);
                    break;
                default:
                    fragment = AboutHimFragment.newInstance(store_id,type);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refresh", observable);
    }
}
