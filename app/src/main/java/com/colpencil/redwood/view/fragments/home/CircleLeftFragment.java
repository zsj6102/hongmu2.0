package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.bean.CategoryItem;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CommentVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.ListResult;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.CommentResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.MyImageLoader;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.CircleLeftPresenter;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.colpencil.redwood.view.activity.discovery.CircleSortActivity;
import com.colpencil.redwood.view.activity.home.MyWebViewActivity;
import com.colpencil.redwood.view.activity.home.PostsActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.CircleLeftAdapter;
import com.colpencil.redwood.view.impl.ICircleLeftView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:藏友圈的圈子
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_circle
)
public class CircleLeftFragment extends ColpencilFragment implements BGARefreshLayoutDelegate, ICircleLeftView {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.listview)
    ListView listView;

    private View header;

    private Banner banner;
    private LinearLayout ll_content;
    private List<CommentVo> mList = new ArrayList<>();
    private CircleLeftAdapter mAdapter;
    private CircleLeftPresenter presenter;
    private boolean isRefresh = false;
    private int page = 1, pageSize = 10;
    private List<CategoryVo> cates = new ArrayList<>();
    private String mType = "";
    private int pos;
    private PopupWindow window;
    private View mview;
    private int intentType;
    private String content;
    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;
    private Observable<RxBusMsg> mObservable;
    private Subscriber mSubscriber;
    private List<BannerVo> bannerVoList = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        mview = view;
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));

        mAdapter = new CircleLeftAdapter(getActivity(), mList, R.layout.circle_left_item);

        header = LayoutInflater.from(getActivity()).inflate(R.layout.circle_left_header, null);
        listView.addHeaderView(header);

        listView.setAdapter(mAdapter);

        initAdapter();

        banner = (Banner) header.findViewById(R.id.circle_banner);
        banner.setImageLoader(new MyImageLoader());
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!ListUtils.listIsNullOrEmpty(bannerVoList)) {
                    BannerVo vo = bannerVoList.get(position - 1);
                    if ("link".equals(vo.getType())) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), MyWebViewActivity.class);
                        intent.putExtra("webviewurl", vo.getHtmlurl());
                        intent.putExtra("type", "banner");
                        startActivity(intent);
                    }
                }
            }
        });
        ll_content = (LinearLayout) header.findViewById(R.id.header);
        header.findViewById(R.id.iv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CircleSortActivity.class);
                startActivity(intent);
            }
        });


        initBus();
    }
    @Override
    public void loadData() {
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            presenter.loadMyTag();
        } else {
            presenter.loadTag();
        }
        presenter.loadImage("13");
        presenter.loadPost(mType, page, pageSize);
    }
    private void initBus() {
        observable = RxBus.get().register("refreshmsg", RefreshMsg.class);
        subscriber = new Subscriber<RefreshMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg refreshMsg) {
                if (2 == refreshMsg.getType() || 3 == refreshMsg.getType() || 4 == refreshMsg.getType()) {
                    listView.setSelection(0);
                    onBGARefreshLayoutBeginRefreshing(refreshLayout);
                }
            }
        };
        observable.subscribe(subscriber);
        mObservable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        mSubscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 58) {
                    presenter.loadMyTag();
                }
            }
        };
        mObservable.subscribe(mSubscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CircleLeftPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadPost(mType, page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadPost(mType, page, pageSize);
        }
        return false;
    }

    private void initAdapter() {
        mAdapter.setListener(new CircleLeftAdapter.ComOnClickListener() {
            @Override
            public void itemClicks(int position) {
                pos = position;
                showPopWindow();
            }

            @Override
            public void likeClick(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    presenter.like(mList.get(pos).getOte_id());
                } else {
                    intentType = 2;
                    showDialog();
                }
            }

            @Override
            public void shareClick(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    RefreshMsg msg = new RefreshMsg();
                    msg.setType(11);
                    msg.setTitle(mList.get(pos).getOte_title());
                    msg.setContent(mList.get(pos).getOte_content());
                    msg.setId(mList.get(pos).getOte_id());
                    if (mList.get(pos).getOriginal_img() != null) {
                        msg.setImage(mList.get(pos).getOriginal_img().get(0));
                    } else {
                        msg.setImage("");
                    }
                    RxBus.get().post("refresh", msg);
                } else {
                    intentType = 3;
                    showDialog();
                }
            }
        });
    }

    @Override
    public void loadTag(ListResult<CategoryVo> result) {
        removeView();
        cates.clear();
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCat_id("");
        categoryVo.setCat_name("全部");
        categoryVo.setCat_child(new ArrayList<CategoryItem>());
        cates.add(categoryVo);
        addView("全部", 0);
        if (result.getCode() == 0) {
            for (int i = 0; i < result.getCatListResult().size(); i++) {
                CategoryVo vo = result.getCatListResult().get(i);
                addView(vo.getCat_name(), i + 1);
            }
            cates.addAll(result.getCatListResult());
        } else if (result.getCode() == 3) {
            presenter.loadTag();
        }
    }

    private void addView(String tagName, int position) {
        TextView tv = new TextView(getActivity());
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding((int) (getResources().getDimension(R.dimen.padding_10dp)), 0,
                (int) (getResources().getDimension(R.dimen.padding_10dp)), 0);
        tv.setBackgroundColor(getResources().getColor(R.color.transparent));
        tv.setTextSize(14);
        if (position == 0) {
            tv.setTextColor(getResources().getColor(R.color.main_red));
        } else {
            tv.setTextColor(getResources().getColor(R.color.white));
        }
        tv.setText(tagName);
        tv.setTag(position);
        tv.setOnClickListener(listener);
        ll_content.addView(tv);
    }

    private void removeView() {
        int count = ll_content.getChildCount();
        while (count > 0) {
            ll_content.removeViewAt(count - 1);
            count = ll_content.getChildCount();
        }
    }

    @Override
    public void loadBanner(final ListResult<BannerVo> result) {
        if (result.getCode() == 0) {
            bannerVoList.clear();
            bannerVoList.addAll(result.getResult());
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < result.getResult().size(); i++) {
                urls.add(result.getResult().get(i).getUrl());
            }
            if (urls.size() > 1) {
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            } else {
                banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
            }
            banner.setImages(urls);
            banner.start();
        }
    }

    @Override
    public void loadLocal(List<CategoryVo> categoryVos) {
    }

    @Override
    public void loadPost(CommentResult result) {
        hideLoading();
        if (result.getCode() == 1) {
            if (page == 1) {
                refresh(result.getNotesList());
            } else {
                loadMore(result.getNotesList());
            }
        }
    }

    @Override
    public void operate(EntityResult<String> result, int type) {
        hideLoading();
        if (type == 1) {    //提交评论
            if (result.getCode() == 1) {
                page = 1;
                presenter.loadPost(mType, page, pageSize);
            } else if (result.getCode() == 3) {
                ToastTools.showShort(getActivity(), result.getMsg());
                intentType = 1;
                intent();
            }
        } else if (type == 2) {     //点赞
            if (result.getCode() == 1 || result.getCode() == 2) {
                page = 1;
                presenter.loadPost(mType, page, pageSize);
            } else if (result.getCode() == 3) {
                ToastTools.showShort(getActivity(), result.getMsg());
                intentType = 2;
                intent();
            }
        }
    }

    private void refresh(List<CommentVo> list) {
        isLoadMore(list);
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void loadMore(List<CommentVo> list) {
        isLoadMore(list);
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mType = cates.get(position).getCat_id();
            showLoading("正在加载中...");
            page = 1;
            presenter.loadPost(mType, page, pageSize);
            changeView(position);
        }
    };

    private void changeView(int position) {
        for (int i = 0; i < ll_content.getChildCount(); i++) {
            TextView tv = (TextView) ll_content.getChildAt(i);
            if (i == position) {
                tv.setTextColor(getResources().getColor(R.color.main_red));
            } else {
                tv.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    private void isLoadMore(List<CommentVo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    private void showPopWindow() {
        if (window == null) {
            window = new PopupWindow(initPopWindow(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(mview, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(mview);
    }

    private View initPopWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.post_reply, null);
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
                content = et_content.getText().toString().trim();
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    if (TextUtils.isEmpty(content)) {
                        ToastTools.showShort(getActivity(), "请输入评论内容");
                        return;
                    }
                    showLoading("正在提交中...");
                    presenter.submitComments(content, mList.get(pos).getOte_id());
                } else {
                    intentType = 1;
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

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                intent();
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void intent() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(StringConfig.REQUEST_CODE, 100);
        startActivityForResult(intent, Constants.REQUEST_LOGIN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            if (intentType == 1) {
                if (!TextUtils.isEmpty(content)) {
                    presenter.submitComments(content, mList.get(pos).getOte_id());
                } else {
                    ToastTools.showShort(getActivity(), "请输入评论的内容");
                }
            } else if (intentType == 2) {
                presenter.like(mList.get(pos).getOte_id());
            }
        }
    }

    @OnClick(R.id.totop_iv)
    void totop() {
        listView.setSelection(0);
    }

    @OnClick(R.id.post_iv)
    void post() {
        if (!SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
            showDialog();
        } else {
            Intent intent = new Intent(getActivity(), PostsActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.iv_home)
    void toHome() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(3);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshmsg", observable);
        RxBus.get().unregister("rxBusMsg", mObservable);
    }
}
