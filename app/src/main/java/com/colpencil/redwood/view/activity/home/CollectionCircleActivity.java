package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.CollectionPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.fragments.home.CircleLeftFragment;
import com.colpencil.redwood.view.fragments.home.CircleRightFragment;
import com.colpencil.redwood.view.impl.ICollectionView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;
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
 * @author 陈宝
 * @Description:藏友圈
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_collection_circle
)
public class CollectionCircleActivity extends ColpencilActivity implements ICollectionView {

    @Bind(R.id.circle_header)
    RelativeLayout toolbar;
    @Bind(R.id.circle_viewpager)
    NoScrollViewPager vp;
    @Bind(R.id.segmentgroup)
    RadioGroup group;
    @Bind(R.id.rb_circle)
    RadioButton rb_circle;
    @Bind(R.id.rb_mine)
    RadioButton rb_mine;
    private int intentType = 0;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"圈子", "我的"};
    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;
    private PopupWindow window;
    private ShareAction action;
    private UMImage image;
    private View mView;
    private CollectionPresenter presenter;
    private String title;
    private String content;
    private int ote_id;
    private String images;

    @Override
    protected void initViews(View view) {
        mView = view;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        toolbar.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));

        setupViewPager();

        fragments.add(new CircleLeftFragment());
        fragments.add(new CircleRightFragment());

        vp.setOffscreenPageLimit(3);
        CirclePageAdapter adapter = new CirclePageAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        action = new ShareAction(this);
        initBus();
    }

    private void setupViewPager() {
        rb_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferencesUtil.getInstance(CollectionCircleActivity.this).getBoolean(StringConfig.ISLOGIN, false)) {
                    group.check(R.id.rb_mine);
                    vp.setCurrentItem(1, false);
                } else {
                    vp.setCurrentItem(0, false);
                    group.check(R.id.rb_circle);
                    intentType = 1;
                    showDialog();
                }
            }
        });
        rb_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.check(R.id.rb_circle);
                vp.setCurrentItem(0, false);
            }
        });
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

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(CollectionCircleActivity.this, LoginActivity.class);
                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                startActivityForResult(intent, Constants.REQUEST_LOGIN);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                if (intentType == 1) {
                    group.check(R.id.rb_circle);
                }
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

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            RefreshMsg msg = new RefreshMsg();
            msg.setType(3);
            RxBus.get().post("refreshmsg", msg);
            if (intentType == 1) {
                vp.setCurrentItem(1, false);
                group.check(R.id.rb_mine);
            } else if (intentType == 2) {
                presenter.share(ote_id);
            }
        }
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refresh", observable);
    }

    @Override
    public void shareResult(CommonResult result) {
        if (result.getCode().equals("3")) {
            intentType = 2;
            showDialog();
        } else if (result.getCode().equals("1")) {
            showPopupWindow(result.getUrl());
        }
    }

    class CirclePageAdapter extends FragmentPagerAdapter {

        public CirclePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private void showPopupWindow(String shareUrl) {
        if (window == null) {
            window = new PopupWindow(initPop(shareUrl),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

    private void share(SHARE_MEDIA platform, String shareUrl) {
        if (TextUtils.isEmpty(images)) {
            image = new UMImage(this,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        } else {
            image = new UMImage(this, images);
        }
        action.setPlatform(platform)
                .setCallback(umShareListener)
                .withTitle(title)
                .withText(content)
                .withTargetUrl(shareUrl)
                .withMedia(image)
                .share();
        dismissSharePop();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastTools.showShort(CollectionCircleActivity.this, "分享成功");
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
            ToastTools.showShort(CollectionCircleActivity.this, "分享失败");
            dismissSharePop();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastTools.showShort(CollectionCircleActivity.this, "分享取消");
            dismissSharePop();
        }
    };
}
