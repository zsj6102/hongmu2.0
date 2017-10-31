package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.NumReturn;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.GoodRightPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.GoodReplyDetail;

import com.colpencil.redwood.view.adapters.GoodCommentAdapter;
import com.colpencil.redwood.view.impl.IGoodRightView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * @author 陈宝
 * @Description:商品评论的Fragment
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_good_right)
public class GoodRightFragment extends ColpencilFragment implements IGoodRightView, BGARefreshLayoutDelegate {

    @Bind(R.id.right_listview)
    ListView listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.comment_count)
    TextView tv_count;
    private GoodCommentAdapter adapter;
    private List<GoodComment> list = new ArrayList<>();
    private GoodRightPresenter presenter;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private int goodsId;
    private int pos;
    public static GoodRightFragment getInstance(int goodid) {
        Bundle bundle = new Bundle();
        bundle.putInt("goodsId", goodid);
        GoodRightFragment fragment = new GoodRightFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        goodsId = getArguments().getInt("goodsId");
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new GoodCommentAdapter(getActivity(), list, R.layout.item_good_comment);
        initAdapter();
        listview.setAdapter(adapter);

    }
    private void initAdapter() {
        adapter.setListener(new GoodCommentAdapter.MyListener() {
            @Override
            public void addLike(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("reply_id", list.get(position).getComment_id() + "");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    map.put("biaoshi", "4");
                    presenter.getLikeResult(map);
                } else {
                    showDialog();
                }
            }

            @Override
            public void commentClick(int position) {
                Intent intent = new Intent(getActivity(), GoodReplyDetail.class);
                intent.putExtra("data", list.get(position));
                intent.putExtra("comment_id", list.get(position).getComment_id());
                startActivity(intent);
            }
        });
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.loadComment(goodsId + "", 1, pageSize);
    }
    private void intent() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(StringConfig.REQUEST_CODE, 100);
        startActivityForResult(intent, Constants.REQUEST_LOGIN);
    }
    @Override
    public void loadData() {
        presenter.loadComment(goodsId + "", 1, pageSize);
        presenter.loadCommentsNum(goodsId);
    }
    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new GoodRightPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    @Override
    public void loadMore(List<GoodComment> comments) {
        isLoreModre(comments);
        refreshLayout.endRefreshing(comments.size());
        list.addAll(comments);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refresh(List<GoodComment> comments) {
        isLoreModre(comments);
        refreshLayout.endRefreshing(comments.size());
        list.clear();
        list.addAll(comments);
        listview.removeAllViewsInLayout();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadNums(NumReturn result) {
        if (result.getCode() == 1) {
            tv_count.setText(result.getNum() + "");
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadComment(goodsId + "", page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadComment(goodsId + "", page, pageSize);
        }
        return false;
    }

    private void isLoreModre(List<GoodComment> comments) {
        if (comments.size() < pageSize)
            isRefresh = false;
        else
            isRefresh = true;
    }

    @Override
    public void addComment(AddResult result) {

    }

    @Override
    public void addLike(ResultInfo<String> resultInfo) {
        if (resultInfo.getCode() == 0) {
            if (list.get(pos).getIsfocus() == 0) {
                list.get(pos).setIsfocus(1);
                list.get(pos).setRe_like_count(list.get(pos).getRe_like_count() + 1);
                adapter.notifyDataSetChanged();
            } else {
                list.get(pos).setIsfocus(0);
                list.get(pos).setRe_like_count(list.get(pos).getRe_like_count() - 1);
                adapter.notifyDataSetChanged();
            }
        }
        ToastTools.showShort(getActivity(), resultInfo.getMessage());
    }
}
