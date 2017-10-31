package com.colpencil.redwood.view.fragments.mine;

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
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.MinePostInfo;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.PostItemInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.MinePostPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.MinePostAdapter;
import com.colpencil.redwood.view.impl.IMinePostView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

import static com.colpencil.redwood.holder.HolderFactory.map;

/**
 * @author QFZ
 * @Description:他的帖子
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_minepost)
public class MinePostFragment extends ColpencilFragment implements IMinePostView {
    @Bind(R.id.minepost_listview)
    ListView minepost_listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private int store_id;
    private MinePostPresenter presenter;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private List<MinePostItem> mdata = new ArrayList<>();
    private MinePostAdapter adapter;
    private int pos;
    private PopupWindow window;
    private View mview;
    private String content;
    private int intentType;

    public static MinePostFragment newInstance(int store_id) {
        MinePostFragment fragment = new MinePostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        mview = view;
        store_id = getArguments().getInt("store_id");
        adapter = new MinePostAdapter(getActivity(), mdata, R.layout.item_minepost);
        initAdapter();
        minepost_listview.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, String> map = new HashMap<>();
                map.put("store_id", store_id + "");
                map.put("page", pageNo + "");
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                map.put("pageSize", pageSize + "");
                presenter.getMinePost(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("store_id", store_id + "");
                    map.put("page", pageNo + "");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("pageSize", pageSize + "");
                    presenter.getMinePost(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));

    }

    private void initAdapter() {
        adapter.setListener(new MinePostAdapter.ComOnClickListener() {
            @Override
            public void itemClicks(int position) {
                pos = position;
                showPopWindow();
            }

            @Override
            public void likeClick(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    presenter.like(mdata.get(pos).getOte_id());
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
                    msg.setTitle(mdata.get(pos).getOte_title());
                    msg.setContent(mdata.get(pos).getOte_content());
                    msg.setId(mdata.get(pos).getOte_id());
                    if (!ListUtils.listIsNullOrEmpty(mdata.get(pos).getImg_list())) {
                        msg.setImage(mdata.get(pos).getImg_list().get(0));
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

    private void showPopWindow() {
        if (window == null) {
            window = new PopupWindow(initPopWindow(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
                    presenter.submitComments(content, mdata.get(pos).getOte_id());
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
                    presenter.submitComments(content, mdata.get(pos).getOte_id());
                } else {
                    ToastTools.showShort(getActivity(), "请输入评论的内容");
                }
            } else if (intentType == 2) {
                presenter.like(mdata.get(pos).getOte_id());
            }
        }
    }

    @Override
    public void loadData() {
        HashMap<String, String> auctionMap = new HashMap<>();
        auctionMap.put("store_id", store_id + "");
        auctionMap.put("page", pageNo + "");
        auctionMap.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        auctionMap.put("pageSize", pageSize + "");
        presenter.getMinePost(pageNo, auctionMap);
        showLoading("");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MinePostPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void operate(EntityResult<String> result, int type) {
        hideLoading();
        if (type == 1) {    //提交评论
            if (result.getCode() == 1) {
                mdata.get(pos).setCon_count(mdata.get(pos).getCon_count() + 1);
                adapter.notifyDataSetChanged();
            } else if (result.getCode() == 3) {
                ToastTools.showShort(getActivity(), result.getMsg());
                intentType = 1;
                intent();
            }
        } else if (type == 2) {     //点赞
            if (result.getCode() == 1 || result.getCode() == 2) {
                if (mdata.get(pos).getIslike() == 0) {
                    mdata.get(pos).setIslike(1);
                    mdata.get(pos).setLike_count(mdata.get(pos).getLike_count() + 1);
                } else {
                    mdata.get(pos).setIslike(0);
                    mdata.get(pos).setLike_count(mdata.get(pos).getLike_count() - 1);
                }
                adapter.notifyDataSetChanged();
            } else if (result.getCode() == 3) {
                ToastTools.showShort(getActivity(), result.getMsg());
                intentType = 2;
                intent();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void refresh(ResultInfo<List<MinePostItem>> data) {
        isLoadMore(data.getData());
        mdata.clear();
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<MinePostItem>> data) {
        isLoadMore(data.getData());
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String msg) {
        ToastTools.showShort(getActivity(), msg);
        hideLoading();
    }

    private void isLoadMore(List<MinePostItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
}
