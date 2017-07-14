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
import android.widget.ListView;
import android.widget.PopupWindow;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CommentVo;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.CircleLeftItemPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.CircleLeftAdapter;
import com.colpencil.redwood.view.impl.ICircleLeftItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.NetUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:圈子的子选项
 * @Email DramaScript@outlook.com
 * @date 2016/8/1
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_common_list
)
public class CircleLeftItemFragment extends ColpencilFragment implements BGARefreshLayoutDelegate, ICircleLeftItemView {

    @Bind(R.id.common_listview)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private CircleLeftAdapter adapter;
    private List<CommentVo> list = new ArrayList<>();
    private CircleLeftItemPresenter presenter;
    private int page = 1;
    private int pageSize = 10;
    private String type;
    private boolean isRefresh = false;
    private PopupWindow window;
    private CommentVo commentVo;
    private View mview;
    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;
    private String content;
    private int intentType = 0; //0表示评论，1表示点赞

    public static CircleLeftItemFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        CircleLeftItemFragment fragment = new CircleLeftItemFragment();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getString("type");
        this.mview = view;
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new CircleLeftAdapter(getActivity(), list, R.layout.circle_left_item);
        listView.setAdapter(adapter);
        adapter.setListener(new CircleLeftAdapter.ComOnClickListener() {
            @Override
            public void itemClicks(int position) {
                commentVo = list.get(position);
                showPopWindow(mview);
            }

            @Override
            public void likeClick(int position) {
                commentVo = list.get(position);
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    presenter.like(commentVo.getOte_id());
                } else {
                    intentType = 1;
                    showDialog();
                }
            }

            @Override
            public void shareClick(int position) {
                commentVo = list.get(position);
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    RefreshMsg msg = new RefreshMsg();
                    msg.setType(11);
                    msg.setTitle(commentVo.getOte_title());
                    msg.setContent(commentVo.getOte_content());
                    msg.setId(commentVo.getOte_id());
                    RxBus.get().post("refresh", msg);
                } else {
                    intentType = 1;
                    showDialog();
                }
            }
        });
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
                if (3 == refreshMsg.getType() || 4 == refreshMsg.getType()) {
                    page = 1;
                    presenter.loadList(type, page, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
        if (NetUtils.isConnected(getActivity())) {
            presenter.loadList(type, page, pageSize);
        } else {
            if (!TextUtils.isEmpty(type)) {
                presenter.loadLocal(Integer.valueOf(type), getActivity());
            }
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CircleLeftItemPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        if (NetUtils.isConnected(getActivity())) {
            presenter.loadList(type, page, pageSize);
        } else {
            presenter.loadLocal(Integer.valueOf(type), getActivity());
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadList(type, page, pageSize);
        }
        return false;
    }

    @Override
    public void refresh(final List<CommentVo> result) {
        isLoadMore(result);
        list.clear();
        list.addAll(result);
        adapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(type)) {
                    DaoUtils.saveNote(result, Integer.valueOf(type), true, getActivity());
                }
            }
        }).start();
    }

    @Override
    public void loadMore(final List<CommentVo> result) {
        isLoadMore(result);
        list.addAll(result);
        adapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(type)) {
                    DaoUtils.saveNote(result, Integer.valueOf(type), true, getActivity());
                }
            }
        }).start();
    }

    private void isLoadMore(List<CommentVo> result) {
        if (result.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
    }

    @Override
    public void loadError(String msg) {
        refreshLayout.endRefreshing(0);
    }

    @Override
    public void submitResult(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 1) {
            page = 1;
            list.clear();
            presenter.loadList(type, page, pageSize);
        } else if (result.getCode() == 3) {
            intentType = 0;
            intent();
        }
        ToastTools.showShort(getActivity(), result.getMsg());
    }

    @Override
    public void loadLocal(List<CommentVo> result) {
        isRefresh = false;
        page = 1;
        list.clear();
        list.addAll(result);
        adapter.notifyDataSetChanged();
        refreshLayout.endRefreshing(0);
    }

    @Override
    public void likeResult(EntityResult<String> result) {
        if (result.getCode() == 2 || result.getCode() == 1) {
            presenter.loadList(type, page, pageSize);
        } else if (result.getCode() == 3) {
            intentType = 1;
            showDialog();
            ToastTools.showShort(getActivity(), result.getMsg());
        } else {
            ToastTools.showShort(getActivity(), result.getMsg());
        }
    }

    private void intent() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(StringConfig.REQUEST_CODE, 100);
        startActivityForResult(intent, Constants.REQUEST_LOGIN);
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
                    presenter.submitComments(content, commentVo.getOte_id());
                } else {
                    intentType = 0;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshmsg", observable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            if (intentType == 0) {
                if (!TextUtils.isEmpty(content)) {
                    presenter.submitComments(content, commentVo.getOte_id());
                } else {
                    ToastTools.showShort(getActivity(), "请输入评论的内容");
                }
            } else if (intentType == 1) {
                presenter.like(commentVo.getOte_id());
            }
        }
    }

}
