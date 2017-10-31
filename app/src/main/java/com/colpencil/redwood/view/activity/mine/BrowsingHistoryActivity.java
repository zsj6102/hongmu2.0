package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.BrowsingHistoryPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.fragments.mine.BrowsingCyclopediaFragment;
import com.colpencil.redwood.view.fragments.mine.BrowsingGoodFragment;
import com.colpencil.redwood.view.fragments.mine.BrowsingPostFragment;
import com.colpencil.redwood.view.impl.IBrowsingHistoryView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;
import com.property.colpencil.colpencilandroidlibrary.Ui.SegmentedGroup;
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
 * 描述：我的浏览记录
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/27 18 07
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_browsinghistory)
public class BrowsingHistoryActivity extends ColpencilActivity implements View.OnClickListener, IBrowsingHistoryView {

    @Bind(R.id.browsingHistory_header)
    RelativeLayout toolbar;

    @Bind(R.id.browsingHistory_header_back)
    ImageView browsingHistory_header_back;

    @Bind(R.id.browsingHistory_viewpager)
    NoScrollViewPager vp;

    @Bind(R.id.browsingHistory_segmentgroup)
    SegmentedGroup myCoupons_segmentgroup;

    @Bind(R.id.browsingHistory_header_delete)
    TextView browsingHistory_header_delete;

    @Bind(R.id.rb_cyclopedia)
    RadioButton rb_cyclopedia;

    @Bind(R.id.rb_post)
    RadioButton rb_post;

    @Bind(R.id.rb_good)
    RadioButton rb_good;

    private BrowsingHistoryPresenter presenter;

    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"百科", "帖子", "商品"};

    private PopupWindow window;
    private ShareAction action;
    private UMImage image;
    private String title;
    private String content;
    private int ote_id;
    private View mView;
    private int type;
    private String imageUrl;

    @Override
    protected void initViews(View view) {
        mView = view;
        toolbar.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));

        setupViewPager();

        fragments.add(new BrowsingCyclopediaFragment());
        fragments.add(new BrowsingPostFragment());
        fragments.add(new BrowsingGoodFragment());
        vp.setOffscreenPageLimit(4);
        MyCouponsPageAdapter adapter = new MyCouponsPageAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        action = new ShareAction(this);
        rb_cyclopedia.setChecked(true);
        browsingHistory_header_back.setOnClickListener(this);
        browsingHistory_header_delete.setOnClickListener(this);
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
                title = msg.getTitle();
                content = msg.getContent();
                imageUrl = msg.getImage();
                ote_id = msg.getId();
                type = msg.getSort();
                if (msg.getType() == 11) {
                    presenter.baikeShare(msg.getCat_id(),ote_id+"");
                }else if(msg.getType() == 10){
                    presenter.share(ote_id);
                }else if(msg.getType() == 12){
                    presenter.goodsShare(ote_id+"");
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void setupViewPager() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    myCoupons_segmentgroup.check(R.id.rb_cyclopedia);
                    rb_cyclopedia.setTextColor(getResources().getColor(R.color.white));
                    rb_post.setTextColor(getResources().getColor(R.color.text_color_first));
                    rb_good.setTextColor(getResources().getColor(R.color.text_color_first));
                }
                if (position == 1) {
                    myCoupons_segmentgroup.check(R.id.rb_post);
                    rb_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_first));
                    rb_post.setTextColor(getResources().getColor(R.color.white));
                    rb_good.setTextColor(getResources().getColor(R.color.text_color_first));
                }
                if (position == 2) {
                    myCoupons_segmentgroup.check(R.id.rb_good);
                    rb_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_first));
                    rb_good.setTextColor(getResources().getColor(R.color.white));
                    rb_post.setTextColor(getResources().getColor(R.color.text_color_first));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myCoupons_segmentgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_cyclopedia:
                        vp.setCurrentItem(0, false);
                        break;
                    case R.id.rb_post:
                        vp.setCurrentItem(1, false);
                        break;
                    case R.id.rb_good:
                        vp.setCurrentItem(2, false);
                        break;
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new BrowsingHistoryPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.browsingHistory_header_back)
    void back() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.browsingHistory_header_back:
                finish();
                break;
            case R.id.browsingHistory_header_delete://清空浏览记录
                if (rb_cyclopedia.isChecked()) {
                    presenter.delet(1, 1);
                } else if (rb_good.isChecked()) {
                    presenter.delet(1, 0);
                } else {
                    presenter.delet(1, 2);
                }
                break;
        }
    }

    @Override
    public void resultInfor(String code, String msg) {
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void shareResult(CommonResult result) {
        if (result.getCode().equals("3")) {
            showDialog();
        } else if (result.getCode().equals("1")) {
            showPopupWindow(result.getUrl());
        }
    }

    @Override
    public void shareBaike(CommonResult result) {
        if (result.getCode().equals("3")) {
            showDialog();
        } else if (result.getCode().equals("1")) {
            showPopupWindow(result.getUrl());
        }
    }

    @Override
    public void shareGoods(CommonResult result) {
        if (result.getCode().equals("3")) {
            showDialog();
        } else if (result.getCode().equals("1")) {
            showPopupWindow(result.getUrl());
        }
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(BrowsingHistoryActivity.this, LoginActivity.class);
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

    private void showPopupWindow(String shareUrl) {
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
        view.findViewById(R.id.popwindow_null).setOnClickListener(new View.OnClickListener() {
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
        if (TextUtils.isEmpty(imageUrl)) {
            image = new UMImage(this, BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        } else {
            image = new UMImage(this, imageUrl);
        }
        action.setPlatform(platform).setCallback(umShareListener).withTitle(title).withText(content).withTargetUrl(shareUrl).withMedia(image).share();
        dismissSharePop();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastTools.showShort(BrowsingHistoryActivity.this, "分享成功");
            if (share_media == SHARE_MEDIA.QQ) {  //QQ
                presenter.recordShare(ote_id, "qq", type);
            } else if (share_media == SHARE_MEDIA.WEIXIN) {   //微信
                presenter.recordShare(ote_id, "friend", type);
            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {    //朋友圈
                presenter.recordShare(ote_id, "weixin", type);
            } else {    //新浪
                presenter.recordShare(ote_id, "xinlang", type);
            }
            dismissSharePop();
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastTools.showShort(BrowsingHistoryActivity.this, "分享失败");
            dismissSharePop();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastTools.showShort(BrowsingHistoryActivity.this, "分享取消");
            dismissSharePop();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            presenter.share(ote_id);
        }
    }

    class MyCouponsPageAdapter extends FragmentPagerAdapter {

        public MyCouponsPageAdapter(FragmentManager fm) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refresh", observable);
    }
}
