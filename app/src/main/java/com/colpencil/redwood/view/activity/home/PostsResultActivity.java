package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CommentVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.SearchPostsPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.CircleLeftAdapter;
import com.colpencil.redwood.view.impl.ISearchPostsView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.colpencil.redwood.view.activity.HomeActivity.result;

/**
 * @author 陈宝
 * @Description:搜索帖子结果
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search_cyclopedia)
public class PostsResultActivity extends ColpencilActivity implements ISearchPostsView, BGARefreshLayoutDelegate {

    @Bind(R.id.search_listview)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.base_header_edit)
    EditText editText;
    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    private List<CommentVo> list = new ArrayList<>();
    private CircleLeftAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private SearchPostsPresenter presenter;
    private String keyword;
    private CommentVo commentVo;
    private View mview;
    private PopupWindow window;
    private String content;
    private int intentType = 0;
    private ShareAction action;
    private UMImage image;
    private PopupWindow popshare;
    private String shareUrl;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        mview = view;
        action = new ShareAction(this);
        editText.setHint("搜索帖子");
        refreshLayout.setDelegate(this);
        refreshLayout2.setDelegate(this);
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout2.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new CircleLeftAdapter(this, list, R.layout.circle_left_item);
        listView.setAdapter(adapter);
        keyword = getIntent().getStringExtra("keyword");
        editText.setText(keyword);
        presenter.loadPosts(keyword, page, pageSize);
        showLoading(Constants.progressName);
        adapter.setListener(new CircleLeftAdapter.ComOnClickListener() {
            @Override
            public void itemClicks(int position) {
                commentVo = list.get(position);
                showPopWindow(mview);
            }

            @Override
            public void likeClick(int position) {
                commentVo = list.get(position);
                if (SharedPreferencesUtil.getInstance(PostsResultActivity.this).getBoolean(StringConfig.ISLOGIN, false)) {
                    presenter.like(commentVo.getOte_id());
                } else {
                    intentType = 1;
                    showDialog();
                }
            }

            @Override
            public void shareClick(int position) {
                commentVo = list.get(position);
                if (SharedPreferencesUtil.getInstance(PostsResultActivity.this).getBoolean(StringConfig.ISLOGIN, false)) {
                    presenter.share(commentVo.getOte_id());
                } else {
                    intentType = 2;
                    showDialog();
                }
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SearchPostsPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<CommentVo> commentVos) {

        isLoadMore(commentVos);
        list.clear();
        list.addAll(commentVos);
        adapter.notifyDataSetChanged();
        if (ListUtils.listIsNullOrEmpty(commentVos)) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    @Override
    public void loadMore(List<CommentVo> commentVos) {
        isLoadMore(commentVos);
        list.addAll(commentVos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError(String msg) {
        hideLoading();
        ToastTools.showShort(this,msg);
    }

    @Override
    public void submitResult(EntityResult<String> commonResult) {
        hideLoading();
        if (commonResult.getCode() == 1) {
            page = 1;
            presenter.loadPosts(keyword, page, pageSize);
        } else if (commonResult.getCode() == 3) {
            intentType = 0;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        }
        ToastTools.showShort(this, commonResult.getMsg());
    }

    @Override
    public void likeResult(EntityResult<String> result) {
        if (result.getCode() == 1 || result.getCode() == 2) {
            presenter.loadPosts(keyword, page, pageSize);
        } else if (result.getCode() == 3) {
            showDialog();
            ToastTools.showShort(this, result.getMessage());
        } else {
            ToastTools.showShort(this, result.getMsg());
        }
    }

    @Override
    public void shareResult(CommonResult result) {
        if (result.getCode().equals("3")) {
            intentType = 2;
            showDialog();
        } else if (result.getCode().equals("1")) {
            shareUrl = result.getUrl();
            showSharePop();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadPosts(keyword, page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadPosts(keyword, page, pageSize);
        }
        return false;
    }

    private void isLoadMore(List<CommentVo> commentVoList) {
        if (commentVoList.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }

        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
    }

    private void loadData() {
        page = 1;
        keyword = editText.getText().toString();

        showLoading(Constants.progressName);
        presenter.loadPosts(keyword, page, pageSize);
        //保存搜索结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(keyword)) {
                    DaoUtils.saveHistory(8, keyword, PostsResultActivity.this);
                }
            }
        }).start();
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @OnClick(R.id.base_header_search)
    void submit() {
        loadData();
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                intentType = 1;
                Intent intent = new Intent(PostsResultActivity.this, LoginActivity.class);
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

    private void showPopWindow(View view) {
        if (window == null) {
            window = new PopupWindow(initPopWindow(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(view);
    }

    private View initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.post_reply, null);
        final EditText et_content = (EditText) view.findViewById(R.id.et_content);
        view.findViewById(R.id.popwindow_null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        view.findViewById(R.id.pop_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et_content.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastTools.showShort(PostsResultActivity.this, "请输入评论内容");
                    return;
                }
                showLoading("正在提交中...");
                presenter.submitComments(content, commentVo.getOte_id());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            if (intentType == 0) {
                presenter.submitComments(content, commentVo.getOte_id());
            } else if (intentType == 1) {
                presenter.like(commentVo.getOte_id());
            } else if (intentType == 2) {
                presenter.share(commentVo.getOte_id());
            }
        }
    }

    private void showSharePop() {
        if (popshare == null) {
            popshare = new PopupWindow(initSharePop(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        popshare.setFocusable(true);
        popshare.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popshare.setBackgroundDrawable(new BitmapDrawable());
        popshare.showAtLocation(mview, Gravity.BOTTOM, 100, 0);
        popshare.showAsDropDown(mview);
    }

    private View initSharePop() {
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
                if (commentVo.getOriginal_img() != null) {
                    image = new UMImage(PostsResultActivity.this,
                            commentVo.getOriginal_img().get(0));
                } else {
                    image = new UMImage(PostsResultActivity.this,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
                }
                action.setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(umShareListener)
                        .withTitle(commentVo.getOte_title())
                        .withText(commentVo.getOte_content())
                        .withTargetUrl(shareUrl)
                        .withMedia(image)
                        .share();
            }
        });
        view.findViewById(R.id.ll_share_weibo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentVo.getOriginal_img() != null) {
                    image = new UMImage(PostsResultActivity.this,
                            commentVo.getOriginal_img().get(0));
                } else {
                    image = new UMImage(PostsResultActivity.this,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
                }
                action.setPlatform(SHARE_MEDIA.SINA)
                        .setCallback(umShareListener)
                        .withTitle(commentVo.getOte_title())
                        .withText(commentVo.getOte_content())
                        .withTargetUrl(shareUrl)
                        .withMedia(image)
                        .share();
            }
        });
        view.findViewById(R.id.ll_share_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentVo.getOriginal_img() != null) {
                    image = new UMImage(PostsResultActivity.this,
                            commentVo.getOriginal_img().get(0));
                } else {
                    image = new UMImage(PostsResultActivity.this,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
                }
                action.setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .withTitle(commentVo.getOte_title())
                        .withText(commentVo.getOte_content())
                        .withTargetUrl(shareUrl)
                        .withMedia(image)
                        .share();
            }
        });
        view.findViewById(R.id.ll_share_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentVo.getOriginal_img() != null) {
                    image = new UMImage(PostsResultActivity.this,
                            commentVo.getOriginal_img().get(0));
                } else {
                    image = new UMImage(PostsResultActivity.this,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
                }
                action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener)
                        .withTitle(commentVo.getOte_title())
                        .withText(commentVo.getOte_content())
                        .withTargetUrl(shareUrl)
                        .withMedia(image)
                        .share();
            }
        });
        return view;
    }

    private void dismissSharePop() {
        if (popshare != null) {
            popshare.dismiss();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastTools.showShort(PostsResultActivity.this, "分享成功");
            if (share_media == SHARE_MEDIA.QQ) {  //QQ
                presenter.recordShare(commentVo.getOte_id(), "qq");
            } else if (share_media == SHARE_MEDIA.WEIXIN) {   //微信
                presenter.recordShare(commentVo.getOte_id(), "friend");
            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {    //朋友圈
                presenter.recordShare(commentVo.getOte_id(), "weixin");
            } else {    //新浪
                presenter.recordShare(commentVo.getOte_id(), "xinlang");
            }
            dismissSharePop();
            loadData();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastTools.showShort(PostsResultActivity.this, "分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastTools.showShort(PostsResultActivity.this, "分享取消");
        }
    };
}
