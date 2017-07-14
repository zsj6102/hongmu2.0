package com.colpencil.redwood.view.activity.cyclopedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.PostsComment;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.cyclopedia.CycloDetailPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.AnswerAdapter;
import com.colpencil.redwood.view.impl.ICycloDetailView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description: 百科详情的Activity
 * @Email DramaScript@outlook.com
 * @date 2016/7/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_cyclopedia_detail
)
public class CyclopediaDetailActivity extends ColpencilActivity implements ICycloDetailView {

    @Bind(R.id.tv_main_title)
    TextView main_title;
    @Bind(R.id.listview)
    ListView listView;
    //收藏
    @Bind(R.id.tv_comment_count)
    TextView tv_count;
    //收藏
    @Bind(R.id.iv_collection_state)
    ImageView iv_state;
    //点赞
    @Bind(R.id.tv_like_num)
    TextView tv_like;
    //点赞
    @Bind(R.id.iv_like)
    ImageView iv_like;

    private View header;
    private HeadViewHolder holder;
    private AnswerAdapter adapter;
    private List<PostsComment> comments = new ArrayList<>();
    private CycloDetailPresenter presenter;
    private PopupWindow window;
    private View view;
    private View bottomView;
    private int page = 1;
    private int pageSize = 10;
    private FootViewHolder footer;
    private boolean hasLoaded = false;
    private int type;

    private ShareAction action;
    private UMImage image;
    private PopupWindow popshare;
    private String shareUrl = "";
    private String content;
    private int resultType = 0;     //0表示提交评论,1表示收藏,2表示点赞
    private EditText et_content;
    private CycloParams params;
    private boolean isFinish = false;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        this.view = view;
        action = new ShareAction(this);
        listView.setVisibility(View.INVISIBLE);
        initHeader();
        initWebView();
        params = (CycloParams) getIntent().getSerializableExtra("params");
        if (params.type.equals("cyclopedia")) {
            type = Constants.KEEP_CYCLOPEDIA;
            main_title.setText(R.string.cyclopedia_detail_title);
            presenter.loadUrl("3", params.article_id);
            presenter.browerGood(3, params.article_id);
            presenter.loadShareUrl(3, params.article_id);
            presenter.loadComments(params.article_id, page, pageSize, "2");
            presenter.likeState(2, params.article_id);
            presenter.viewState(params.article_id, 2);
        } else {
            type = Constants.KEEP_NEWS;
            main_title.setText("新闻详情");
            presenter.loadUrl("8", params.article_id);
            presenter.loadShareUrl(8, params.article_id);
            presenter.loadComments(params.article_id, page, pageSize, "3");
            presenter.likeState(4, params.article_id);
            presenter.viewState(params.article_id, 4);
        }
        presenter.loadContent(params.article_id);
        holder.tv_time.setText(TimeUtil.getTimeDiffDay(params.time, System.currentTimeMillis()));
    }

    private void initWebView() {
        WebSettings settings = holder.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);    //提高渲染的优先级
        settings.setBlockNetworkImage(true);    //把图片加载放到最后来加载渲染
//        settings.setAppCacheEnabled(true);      //开启缓存功能
//        settings.setDatabaseEnabled(true);      //应用可以有数据库
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);    //设置缓存模式
        holder.webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("helpcenter")) {
                    Intent intent = new Intent(CyclopediaDetailActivity.this, WebViewActivity.class);
                    intent.putExtra("key", "info");
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                holder.webView.getSettings().setBlockNetworkImage(false);
                hideLoading();
                isFinish = true;
                listView.setVisibility(View.VISIBLE);
                holder.tv_time.setVisibility(View.VISIBLE);
            }

        });
    }

    private void initHeader() {
        header = LayoutInflater.from(this).inflate(R.layout.webview_header, null);
        holder = new HeadViewHolder(header);
        listView.addHeaderView(header);
        bottomView = LayoutInflater.from(this).inflate(R.layout.comment_bottom, null);
        footer = new FootViewHolder(bottomView);
        listView.addFooterView(bottomView);

        adapter = new AnswerAdapter(this, comments, R.layout.item_comment_detail_answer, 1);
        listView.setAdapter(adapter);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CycloDetailPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_back)
    void BackClick() {
        finish();
    }

    @Override
    public void loadUrl(String url) {
        showLoading("正在加载中...");
        holder.webView.loadUrl(url);
    }

    @Override
    public void loadComments(PCommentResult result) {
        if (result.getCode().equals("0")) {
            comments.clear();
            comments.addAll(result.getReplyList());
            pageSize = result.getCount();
            footer.tv.setText("查看全部" + pageSize + "条评论");
            tv_like.setText(result.getDianzhan_num() + "");
            tv_count.setText(pageSize + "");
            if (hasLoaded || pageSize < 10) {
                footer.tv.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void submitResult(EntityResult<String> result) {
        if (result.getCode() == 0) {
            pageSize += 1;
            if (type == Constants.KEEP_CYCLOPEDIA) {
                presenter.loadComments(params.article_id, page, pageSize, "2");
            } else {
                presenter.loadComments(params.article_id, page, pageSize, "3");
            }
            if (et_content != null) {
                et_content.setText("");
            }
            GoodBusMsg msg = new GoodBusMsg();
            msg.setType("refresh");
            RxBus.get().post(StringConfig.GOODSBUS, msg);
        } else if (result.getCode() == 3) {   //未登录
            resultType = 0;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        }
        ToastTools.showShort(this, result.getMessage());
    }

    @Override
    public void checkStateResult(EntityResult<String> result) {
        if (result.getCode() == 0) {
            if (result.getFavorite() == 1) {
                iv_state.setImageResource(R.mipmap.favorited_icon);
            } else {
                iv_state.setImageResource(R.mipmap.detail_collection_icon);
            }
        } else {
            iv_state.setImageResource(R.mipmap.detail_collection_icon);
        }
    }

    @Override
    public void keepResult(EntityResult<String> result) {
        if (result.getCode() == 1) {
            iv_state.setImageResource(R.mipmap.favorited_icon);
        } else if (result.getCode() == 3) {
            resultType = 1;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
            iv_state.setImageResource(R.mipmap.detail_collection_icon);
            ToastTools.showShort(this, result.getMsg());
        } else {
            iv_state.setImageResource(R.mipmap.detail_collection_icon);
        }
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(4);
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }

    @Override
    public void shareResult(EntityResult<String> result) {
        Log.e("result", result.toString());
        if (result.getCode() == 1) {
            shareUrl = result.getUrl();
        }
    }

    @Override
    public void likeResult(EntityResult<String> result) {
        if (result.getCode() == 2) {
            iv_like.setImageResource(R.mipmap.iv_like_icon);
            tv_like.setText(result.getCounts() + "");
        } else if (result.getCode() == 1) {
            iv_like.setImageResource(R.mipmap.iv_unlike_icon);
            tv_like.setText(result.getCounts() + "");
        } else {
            iv_like.setImageResource(R.mipmap.iv_unlike_icon);
            ToastTools.showShort(this, result.getMsg());
        }
    }

    @Override
    public void likeStateResult(EntityResult<String> result) {
        if (result.getCode() == 1) {
            if (result.getLike() == 1) {
                iv_like.setImageResource(R.mipmap.iv_like_icon);
            } else {
                iv_like.setImageResource(R.mipmap.iv_unlike_icon);
            }
        } else {
            iv_like.setImageResource(R.mipmap.iv_unlike_icon);
        }
    }

    @OnClick(R.id.input_content)
    void replyOnClick() {
        showPopWindow(view);
    }

    @OnClick(R.id.iv_share)
    void shareOnClick() {
        if (!TextUtils.isEmpty(shareUrl)) {
            showShare(view);
        } else {
            resultType = 3;
            showDialog();
        }
    }

    @OnClick(R.id.iv_home)
    void toHome() {
        if (isFinish) {
            RxBusMsg rxBusMsg = new RxBusMsg();
            rxBusMsg.setType(3);
            RxBus.get().post("rxBusMsg", rxBusMsg);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showPopWindow(View view) {
        if (window == null) {
            window = new PopupWindow(initPopWindow(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(view);
    }

    private View initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.post_reply, null);
        et_content = (EditText) view.findViewById(R.id.et_content);
        view.findViewById(R.id.popwindow_null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        view.findViewById(R.id.pop_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et_content.getText().toString().trim();
                if (SharedPreferencesUtil.getInstance(CyclopediaDetailActivity.this).getBoolean(StringConfig.ISLOGIN, false)) {
                    if (TextUtils.isEmpty(content)) {
                        ToastTools.showShort(CyclopediaDetailActivity.this, "请输入评论内容");
                        return;
                    }
                    if (type == Constants.KEEP_CYCLOPEDIA) {
                        presenter.submitComment(params.article_id, content, "2");
                    } else {
                        presenter.submitComment(params.article_id, content, "3");
                    }
                } else {
                    resultType = 0;
                    showDialog();
                }
                dismissPop();
            }
        });
        return view;
    }

    private void dismissPop() {
        if (window != null) {
            window.dismiss();
        }
    }

    private void showShare(View view) {
        if (popshare == null) {
            popshare = new PopupWindow(initSharepop(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        popshare.setFocusable(true);
        popshare.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popshare.setBackgroundDrawable(new BitmapDrawable());
        popshare.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        popshare.showAsDropDown(view);
    }

    private View initSharepop() {
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
                share(SHARE_MEDIA.QQ);
            }
        });
        view.findViewById(R.id.ll_share_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.SINA);
            }
        });
        view.findViewById(R.id.ll_share_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN);
            }
        });
        view.findViewById(R.id.ll_share_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        return view;
    }

    private void dismissSharePop() {
        if (popshare != null) {
            popshare.dismiss();
        }
    }

    private void share(SHARE_MEDIA platform) {
        String contents;
        if (TextUtils.isEmpty(params.content)) {
            if (type == Constants.KEEP_CYCLOPEDIA) {
                contents = "百科分享";
            } else {
                contents = "新闻分享";
            }
        } else {
            contents = params.content;
        }
        if (TextUtils.isEmpty(params.image)) {
            image = new UMImage(CyclopediaDetailActivity.this,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
            action.setPlatform(platform)
                    .setCallback(umShareListener)
                    .withTitle(params.title)
                    .withText(contents)
                    .withTargetUrl(shareUrl)
                    .withMedia(image)
                    .share();
        } else {
            image = new UMImage(CyclopediaDetailActivity.this,
                    params.image);
            action.setPlatform(platform)
                    .setCallback(umShareListener)
                    .withTitle(params.title)
                    .withText(contents)
                    .withTargetUrl(shareUrl)
                    .withMedia(image)
                    .share();
        }

    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            int types;
            if (type == Constants.KEEP_CYCLOPEDIA) {
                types = 2;
            } else {
                types = 4;
            }
            if (share_media == SHARE_MEDIA.QQ) {  //QQ
                presenter.addUpSharenum(types, "qq", params.article_id);
            } else if (share_media == SHARE_MEDIA.WEIXIN) {   //微信
                presenter.addUpSharenum(types, "friend", params.article_id);
            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {    //朋友圈
                presenter.addUpSharenum(types, "weixin", params.article_id);
            } else {    //新浪
                presenter.addUpSharenum(types, "xinlang", params.article_id);
            }
            dismissSharePop();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastTools.showShort(CyclopediaDetailActivity.this, "分享失败");
            dismissPop();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            dismissPop();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            if (type == Constants.KEEP_CYCLOPEDIA) {
                presenter.likeState(2, params.article_id);
                presenter.viewState(params.article_id, 2);
            } else {
                presenter.likeState(4, params.article_id);
                presenter.viewState(params.article_id, 4);
            }

            if (resultType == 0) {
                if (TextUtils.isEmpty(content)) {
                    ToastTools.showShort(this, "请输入评论内容");
                    return;
                }
                if (type == Constants.KEEP_CYCLOPEDIA) {
                    presenter.submitComment(params.article_id, content, "2");
                } else {
                    presenter.submitComment(params.article_id, content, "3");
                }
            } else if (resultType == 1) {
                if (type == Constants.KEEP_CYCLOPEDIA) {
                    presenter.keepCyclopedia(params.article_id, Constants.KEEP_CYCLOPEDIA);
                } else {
                    presenter.keepCyclopedia(params.article_id, Constants.KEEP_NEWS);
                }
            } else if (resultType == 2) {
                if (type == Constants.KEEP_CYCLOPEDIA) {      //百科
                    presenter.like(2, params.article_id);
                } else {
                    presenter.like(4, params.article_id);
                }
            } else if (resultType == 3) {
                if (params.type.equals("cyclopedia")) {
                    presenter.loadShareUrl(3, params.article_id);
                } else {
                    presenter.loadShareUrl(8, params.article_id);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismissPop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.unbind();
        footer.unbind();
    }

    @OnClick(R.id.iv_collection_state)
    void keepOnClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            if (type == Constants.KEEP_CYCLOPEDIA) {
                presenter.keepCyclopedia(params.article_id, Constants.KEEP_CYCLOPEDIA);
            } else {
                presenter.keepCyclopedia(params.article_id, Constants.KEEP_NEWS);
            }
        } else {
            resultType = 1;
            showDialog();
        }
    }

    @OnClick(R.id.iv_like)
    void likeClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            if (type == Constants.KEEP_CYCLOPEDIA) {      //百科
                presenter.like(2, params.article_id);
            } else {
                presenter.like(4, params.article_id);
            }
        } else {
            resultType = 2;
            showDialog();
        }
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔", "确定", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(CyclopediaDetailActivity.this, LoginActivity.class);
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

    class HeadViewHolder {

        @Bind(R.id.header_webview)
        WebView webView;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }
    }

    class FootViewHolder {

        @Bind(R.id.comment_tv)
        TextView tv;

        public FootViewHolder(View itemview) {
            ButterKnife.bind(this, itemview);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.comment_tv)
        void viewAllOnClick() {
            hasLoaded = true;
            if (type == Constants.KEEP_CYCLOPEDIA) {
                presenter.loadComments(params.article_id, page, pageSize, "2");
            } else {
                presenter.loadComments(params.article_id, page, pageSize, "3");
            }
        }

    }

}
