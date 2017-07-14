package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.view.adapters.CardWallAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_common_list
)
public class CardWallFragment extends ColpencilFragment implements BGARefreshLayoutDelegate {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.common_listview)
    ListView listview;

    private int page = 1, pageSize = 10;
    private boolean isRefresh = false;
    private CardWallAdapter mAdapter;
    private List<CardWallInfo> mlist = new ArrayList<>();

    public static CardWallFragment newInstance(int type) {
        CardWallFragment fragment = new CardWallFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        mAdapter = new CardWallAdapter(getActivity(), mlist, R.layout.item_card_wall);
        listview.setAdapter(mAdapter);
        loadData();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        loadData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            loadData();
        }
        return false;
    }

    private void loadData() {
        for (int i = 0; i < 10; i++) {
            CardWallInfo info = new CardWallInfo();
            info.head = "http://c11.eoemarket.com/app0/330/330762/icon/619972.png";
            mlist.add(info);
        }
        mAdapter.notifyDataSetChanged();
        refreshLayout.endRefreshing(0);
    }
}
