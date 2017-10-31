package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.bean.RatedInfo;
import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.present.mine.EncycloPresenter;
import com.colpencil.redwood.present.mine.RatedPresenter;
import com.colpencil.redwood.view.adapters.EncyclopediasAdapter;
import com.colpencil.redwood.view.adapters.RatedAdapter;
import com.colpencil.redwood.view.impl.IRatedView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_rated
)
public class RatedFragment extends ColpencilFragment implements IRatedView {
    @Bind(R.id.rated_listview)
    ListView rated_listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private RatedPresenter presenter;
    private List<RatedItem> mdata = new ArrayList<>();
    private int store_id;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private RatedAdapter adapter;
    public static RatedFragment newInstance(int store_id ) {
        RatedFragment fragment = new RatedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initViews(View view) {
        store_id = getArguments().getInt("store_id");
        adapter = new RatedAdapter(getActivity(),mdata,R.layout.item_rated);
        rated_listview.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, String> map = new HashMap<>();
                map.put("store_id", store_id + "");
                map.put("page", pageNo + "");
                map.put("pageSize", pageSize + "");
                presenter.getRatedList(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("store_id", store_id + "");
                    map.put("page", pageNo + "");
                    map.put("pageSize",pageSize + "");
                    presenter.getRatedList(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new RatedPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    @Override
    public void loadData() {
        HashMap<String, String> auctionMap = new HashMap<>();
        auctionMap.put("store_id", store_id + "");
        auctionMap.put("page", pageNo + "");
        auctionMap.put("pageSize", pageSize + "");
        presenter.getRatedList(pageNo, auctionMap);
        showLoading("");
    }

    @Override
    public void refresh(ResultInfo<List<RatedItem>> result) {
        isLoadMore(result.getData());
        mdata.clear();
        mdata.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<RatedItem>> result) {
        isLoadMore(result.getData());
        mdata.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String msg) {
        ToastTools.showShort(getActivity(),msg);
        hideLoading();
    }
    private void isLoadMore(List<RatedItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
}
