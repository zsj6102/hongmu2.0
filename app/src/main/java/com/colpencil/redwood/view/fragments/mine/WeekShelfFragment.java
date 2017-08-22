package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PlainRack;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.present.mine.ShelfPresenter;

import com.colpencil.redwood.view.adapters.ShelfAdapter;
import com.colpencil.redwood.view.impl.IShelfView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;


/**
 * 普通货架
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_shelf_layout
)
public class WeekShelfFragment extends ColpencilFragment  implements IShelfView {

    private int pageNo = 1, pageSize = 10;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private ShelfPresenter presenter;
    private int store_id;
    private boolean isRefresh = false;
    private List<PlainRack> mdata = new ArrayList<>();
    private ShelfAdapter adapter;
    @Bind(R.id.gridview)
    GridView gridView;
    public static WeekShelfFragment newInstance(int store_id){
        WeekShelfFragment fragment = new WeekShelfFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id",store_id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initViews(View view) {
        store_id = getArguments().getInt("store_id");
        adapter = new ShelfAdapter(getActivity(),mdata,R.layout.item_brand_good);
        gridView.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String,String> map=new HashMap<>();
                map.put("store_id",store_id+"");
                map.put("page",pageNo+"");
                map.put("pageSize",pageSize+"");
                showLoading("加载中...");
                presenter.getPlainRack(pageNo,map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String,String> map=new HashMap<>();
                    map.put("store_id",store_id+"");
                    map.put("page",pageNo+"");
                    map.put("pageSize",pageSize+"");
                    showLoading("加载中...");
                    presenter.getPlainRack(pageNo,map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
    }
    @Override
    public void loadData() {
        HashMap<String,String> map = new HashMap<>();

        map.put("store_id",store_id+"");
        map.put("page",""+pageNo);
        map.put("pageSize",""+pageSize);
        presenter.getPlainRack(pageNo,map);
    }
    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ShelfPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadMore(ResultInfo<List<PlainRack>> result) {
        isLoadMore(result.getData());
        mdata.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(ResultInfo<List<PlainRack>> result) {
        isLoadMore(result.getData());
        mdata.clear();
        mdata.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String msg) {

    }
    private void isLoadMore(List<PlainRack> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
}