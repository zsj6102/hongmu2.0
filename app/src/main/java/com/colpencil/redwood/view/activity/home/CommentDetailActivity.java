package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.PostsComment;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.CommonResult;
import com.colpencil.redwood.bean.result.PCommentResult;
import com.colpencil.redwood.bean.result.PostsResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.CommentDetailPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.ReplyDetail;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.adapters.AnswerAdapter;
import com.colpencil.redwood.view.adapters.ImageAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.ICommentDetailView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

import static com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil.getTimeDiffDay;

/**
 * @author 陈宝
 * @Description:帖子详情的Activity
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_comment_detail)
public class CommentDetailActivity extends ColpencilActivity implements ICommentDetailView {

    @Bind(R.id.base_listview)
    ListView base_listview;
    @Bind(R.id.tv_comment_num)
    TextView tv_comment_num;
    @Bind(R.id.tv_like_num)
    TextView tv_like_num;
    @Bind(R.id.iv_like)
    ImageView iv_like;
    @Bind(R.id.iv_keep)
    ImageView iv_keep;
    private int ote_id;
    private View header;
    private HeadViewHolder holder;
    private List<String> imglist = new ArrayList<>();//图片列表
    private ImageAdapter imgAdapter;//图片适配器
    private List<PostsComment> posts = new ArrayList<>();//评论列表
    private AnswerAdapter ansAdapter;//评论适配器
    private CommentDetailPresenter presenter;
    private View view;
    private PopupWindow window;
    private int page = 1;
    private int pageSize = 10;
    private boolean hasLoaded = false;
    private String videourl;

    private ShareAction action;
    private UMImage image;
    private PopupWindow popshare;
    private String shareUrl = "";
    private int type = 0;
    private String content;
    private EditText et_content;
    private PostsResult mResult;
    private int pos;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        this.view = view;
        action = new ShareAction(this);
        ote_id = getIntent().getIntExtra("commentid", 0);
        initHeader();
        imgAdapter = new ImageAdapter(this, imglist, R.layout.item_circle_image);
        holder.first_listview.setAdapter(imgAdapter);
        ansAdapter = new AnswerAdapter(this, posts, R.layout.item_comment_detail_answer, 0);
        initAdapter();
        initBus();
        holder.second_listview.setAdapter(ansAdapter);
        showLoading("");
        if (NetUtils.isConnected(this)) {
            presenter.loadImageList(ote_id + "");
            presenter.loadComments(ote_id + "", page, pageSize);
            presenter.loadKeepState(ote_id);
            presenter.browerGood(ote_id);
            presenter.loadShareUrl(ote_id);
            presenter.loadLikeState(ote_id);
        } else {
            presenter.loadLocal(ote_id, this);
        }
        holder.first_listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        holder.first_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommentDetailActivity.this, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) imglist);
                startActivity(intent);
            }
        });
    }

    private void initHeader() {
        base_listview.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        header = LayoutInflater.from(this).inflate(R.layout.item_comment_detail_header, null);
        holder = new HeadViewHolder(header);
        base_listview.addHeaderView(header);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CommentDetailPresenter();
        return presenter;
    }
    private void initBus(){
        observable = RxBus.get().register("rxBusMsg",RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(rxBusMsg.getType() == 123){
                    presenter.loadComments(ote_id + "", page, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }
    private void initAdapter() {
        ansAdapter.setListener(new AnswerAdapter.MyListener() {
            @Override
            public void commentClick(int position) {
                Intent intent = new Intent(CommentDetailActivity.this, ReplyDetail.class);
                NodeReplyItem item = new NodeReplyItem();
                item.setRe_id(posts.get(position).getRe_id());
                item.setRe_face(posts.get(position).getFace());
                item.setRe_store_name(posts.get(position).getNickname());
                item.setRe_member_photo(posts.get(position).getMember_photo());
                item.setRe_content(posts.get(position).getRe_content());
                String time;
                if (posts.get(position).getCreatetime() == 0) {
                    time = TimeUtil.getTimeDiffDay(posts.get(position).getTime(), System.currentTimeMillis());
                } else {
                    time = getTimeDiffDay(posts.get(position).getCreatetime(), System.currentTimeMillis());
                }
                item.setRe_create_time(time);
                intent.putExtra("data", item);
                intent.putExtra("type", 0);
                startActivity(intent);
            }

            @Override
            public void addLike(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("reply_id", posts.get(position).getRe_id() + "");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    map.put("biaoshi", "5");
                    presenter.getLikeResult(map);
                } else {
                    type = 50;
                    showDialog();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg",observable);
    }
    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadPosts(PostsResult result) {
        hideLoading();
        mResult = result;
        if (result.getCode().equals("1")) {
            videourl = result.getUrl();
            ImageLoaderUtils.loadImage(this, result.getFace(), holder.iv);
            ImageLoaderUtils.loadImage(this, result.getMember_photo(), holder.iv_level);
            holder.tv_nickname.setText(result.getNickname());
            holder.tv_post_title.setText(result.getOte_title());
            holder.tv_content.setText(result.getOte_content());
            holder.tv_time.setText(TimeUtil.getTimeDiffDay(result.getCreattime(), result.getSystemTime()));
            holder.tv_url.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            holder.tv_url.setText(videourl);
            tv_comment_num.setText(result.getCom_count() + "");
            tv_like_num.setText(result.getLike_count() + "");
            holder.tv_count.setText("查看全部" + result.getCom_count() + "条评论");
            pageSize = result.getCom_count();
            if (!ListUtils.listIsNullOrEmpty(result.getOte_images())) {
                imglist.clear();
                imglist.addAll(result.getOte_images());
                imgAdapter.notifyDataSetChanged();
            }
            if (result.getOte_digest() == 0) {
                holder.tv_addplus.setVisibility(View.GONE);
            } else {
                holder.tv_addplus.setVisibility(View.VISIBLE);
            }
            if (result.getOte_stick() == 0) {
                holder.tv_sticky.setVisibility(View.GONE);
            } else {
                holder.tv_sticky.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void loadComment(PCommentResult result) {
        hideLoading();
        posts.clear();
        posts.addAll(result.getReplyList());
        if (hasLoaded || result.getReplyList().size() < 10) {
            holder.tv_count.setVisibility(View.GONE);
        } else {
            holder.tv_count.setVisibility(View.VISIBLE);
        }
        holder.tv_count.setText("查看全部" + pageSize + "条评论");
        ansAdapter.notifyDataSetChanged();
    }

    @Override
    public void submitResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            page = 1;
            pageSize++;
            presenter.loadComments(ote_id + "", page, pageSize);
            tv_comment_num.setText(result.getNum() + "");
            if (et_content != null) {
                et_content.setText("");
            }
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        } else if (result.getCode() == 3) {
            type = 2;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
            ToastTools.showShort(this, result.getMsg());
        }
    }

    @Override
    public void keepResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            iv_keep.setImageResource(R.mipmap.favorited_icon);
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        } else if (result.getCode() == 3) {
            type = 0;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
            ToastTools.showShort(this, result.getMessage());
        } else {
            iv_keep.setImageResource(R.mipmap.detail_collection_icon);
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        }
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(26);
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }

    @Override
    public void likeResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 2) {
            iv_like.setImageResource(R.mipmap.iv_like_icon);
            int num = Integer.valueOf(tv_like_num.getText().toString());
            tv_like_num.setText(num + 1 + "");
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        } else if (result.getCode() == 1) {
            iv_like.setImageResource(R.mipmap.iv_unlike_icon);
            int num = Integer.valueOf(tv_like_num.getText().toString());
            tv_like_num.setText(num - 1 + "");
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        } else if (result.getCode() == 3) {
            type = 1;
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
            ToastTools.showShort(this, result.getMessage());
        } else {
            iv_like.setImageResource(R.mipmap.iv_unlike_icon);
            ToastTools.showShort(this, result.getMessage());
        }
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(26);
        RxBus.get().post("rxBusMsg", rxBusMsg);
    }

    @Override
    public void stateResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            if (result.getFavorite() == 1) {
                iv_keep.setImageResource(R.mipmap.favorited_icon);
            } else {
                iv_keep.setImageResource(R.mipmap.detail_collection_icon);
            }
        } else {
            iv_keep.setImageResource(R.mipmap.detail_collection_icon);
        }
    }

    @Override
    public void shareResult(CommonResult result) {
        if ("1".equals(result.getCode())) {
            shareUrl = result.getUrl();
        }
    }

    @Override
    public void likeState(EntityResult<String> result) {
        hideLoading();
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

    /**
     * 收藏
     */
    @OnClick(R.id.iv_keep)
    void keepOnClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            presenter.keepComments(ote_id);
        } else {
            type = 0;
            showDialog();
        }
    }

    /**
     * 点赞
     */
    @OnClick(R.id.iv_like)
    void likeOnClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            showLoading("正在提交数据中...");
            presenter.likeComments(ote_id);
        } else {
            type = 1;
            showDialog();
        }
    }

    /**
     * 分享
     */
    @OnClick(R.id.iv_share)
    void shareOnClick() {
        if (!TextUtils.isEmpty(shareUrl)) {
            showShare(view);
        } else {
            type = 3;
            showDialog();
        }
    }

    @OnClick(R.id.iv_home)
    void toHome() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(3);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔！", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(CommentDetailActivity.this, LoginActivity.class);
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
            window = new PopupWindow(initPopWindow(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
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
        view.findViewById(R.id.pop_send).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et_content.getText().toString().trim();
                if (SharedPreferencesUtil.getInstance(CommentDetailActivity.this).getBoolean(StringConfig.ISLOGIN, false)) {
                    if (TextUtils.isEmpty(content)) {
                        ToastTools.showShort(CommentDetailActivity.this, "请输入评论内容");
                        return;
                    }
                    showLoading("");
                    presenter.submitComments(content, ote_id);
                } else {
                    type = 2;
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
            popshare = new PopupWindow(initSharepop(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        popshare.setFocusable(true);
        popshare.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popshare.setBackgroundDrawable(new BitmapDrawable());
        popshare.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        popshare.showAsDropDown(view);
    }

    @Override
    public void getNoteList(ResultInfo<List<NodeReplyItem>> result) {

    }

    @Override
    public void addLike(ResultInfo<String> resultInfo) {
        if (resultInfo.getCode() == 0) {
            if (posts.get(pos).getIsfocus() == 0) {
                posts.get(pos).setIsfocus(1);
                posts.get(pos).setRe_like_count(posts.get(pos).getRe_like_count() + 1);
                ansAdapter.notifyDataSetChanged();
            } else {
                posts.get(pos).setIsfocus(0);
                posts.get(pos).setRe_like_count(posts.get(pos).getRe_like_count() - 1);
                ansAdapter.notifyDataSetChanged();
            }
        }
        ToastTools.showShort(this, resultInfo.getMessage());
    }

    private View initSharepop() {
        View view = LayoutInflater.from(this).inflate(R.layout.popupwindow_share, null);
        view.findViewById(R.id.close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissSharePop();
            }
        });
        view.findViewById(R.id.ll_share_qq).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QQ);
            }
        });
        view.findViewById(R.id.ll_share_weibo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.SINA);
            }
        });
        view.findViewById(R.id.ll_share_wechat).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN);
            }
        });
        view.findViewById(R.id.ll_share_circle).setOnClickListener(new OnClickListener() {
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
        if (mResult != null && mResult.getCode().equals("1")) {
            if (mResult.getOte_images() == null) {
                image = new UMImage(CommentDetailActivity.this, BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
            } else {
                image = new UMImage(CommentDetailActivity.this, mResult.getOte_images().get(0));
            }
            action.setPlatform(platform).setCallback(umShareListener).withTitle(mResult.getOte_title()).withText(mResult.getOte_content()).withTargetUrl(shareUrl).withMedia(image).share();
        } else {
            image = new UMImage(CommentDetailActivity.this, BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
            action.setPlatform(platform).setCallback(umShareListener).withTitle("帖子分享").withText("帖子分享").withTargetUrl(shareUrl).withMedia(image).share();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            if (share_media == SHARE_MEDIA.QQ) {  //QQ
                presenter.addUpSharenum(1, "qq", ote_id);
            } else if (share_media == SHARE_MEDIA.WEIXIN) {   //微信
                presenter.addUpSharenum(1, "friend", ote_id);
            } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {    //朋友圈
                presenter.addUpSharenum(1, "weixin", ote_id);
            } else {    //新浪
                presenter.addUpSharenum(1, "xinlang", ote_id);
            }
            dismissSharePop();
            RefreshMsg msg = new RefreshMsg();
            msg.setType(4);
            RxBus.get().post("refreshmsg", msg);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastTools.showShort(CommentDetailActivity.this, "分享失败");
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
        if (resultCode == Constants.REQUEST_LOGIN) {
            if (type == 0) {
                presenter.keepComments(ote_id);
            } else if (type == 1) {
                presenter.likeComments(ote_id);
            } else if (type == 2) {
                if (!TextUtils.isEmpty(content)) {
                    presenter.submitComments(content, ote_id);
                } else {
                    ToastTools.showShort(this, "请输入评论的内容");
                }
            } else if (type == 3) {
                presenter.loadShareUrl(ote_id);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismissPop();
        dismissSharePop();
    }

    class HeadViewHolder {

        @Bind(R.id.item_comment_image_gridview)
        MosaicGridView first_listview;
        @Bind(R.id.item_comment_answer_listview)
        MosaicListView second_listview;
        @Bind(R.id.comment_detail_userhead)
        SelectableRoundedImageView iv;
        @Bind(R.id.comment_detail_nickname)
        TextView tv_nickname;
        @Bind(R.id.comment_detail_time)
        TextView tv_time;
        @Bind(R.id.comment_detail_title)
        TextView tv_post_title;
        @Bind(R.id.comment_detail_content)
        TextView tv_content;
        @Bind(R.id.tv_comment_count)
        TextView tv_count;
        @Bind(R.id.comment_url)
        TextView tv_url;
        @Bind(R.id.add_plus)
        TextView tv_addplus;
        @Bind(R.id.iv_sticky)
        TextView tv_sticky;
        @Bind(R.id.iv_level)
        ImageView iv_level;

        public HeadViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.tv_comment_count)
        void loadAllOnClick() {
            hasLoaded = true;
            presenter.loadComments(ote_id + "", page, pageSize);
        }

        @OnClick(R.id.comment_url)
        void urlOnClick() {
            Intent intent = new Intent(CommentDetailActivity.this, WebViewActivity.class);
            intent.putExtra(StringConfig.WEBVIEWURL, videourl);
            startActivity(intent);
        }

        @OnClick(R.id.ll_condition)
        void condition() {
            Intent intent = new Intent(CommentDetailActivity.this, WebViewActivity.class);
            intent.putExtra("key", "info");
            startActivity(intent);
        }
    }

}
