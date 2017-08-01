package com.colpencil.redwood.view.fragments.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.present.home.GoodRightPresenter;
import com.colpencil.redwood.view.adapters.GoodCommentAdapter;
import com.colpencil.redwood.view.impl.IGoodRightView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

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
        listview.setAdapter(adapter);

    }

    @Override
    public void loadData() {
        presenter.loadComment(goodsId + "", page, pageSize);
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadNums(EntityResult<String> result) {
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
}
