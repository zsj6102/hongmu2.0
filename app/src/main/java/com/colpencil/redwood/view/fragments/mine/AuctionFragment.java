package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.present.mine.AuctionPresenter;
import com.colpencil.redwood.view.adapters.AuctionItemAdapter;
import com.colpencil.redwood.view.impl.IAuctionView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;



/**
 * @author QFZ
 * @Description:架上拍品
 * @Email DramaScript@outlook.com
 * @date 2017-03-09
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_auction)
public class AuctionFragment extends ColpencilFragment implements IAuctionView {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private int store_id;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private List<JiashangItem> mdata = new ArrayList<>();
    private AuctionPresenter presenter;
    private AuctionItemAdapter adapter;

    public static AuctionFragment newInstance(int store_id) {
        AuctionFragment fragment = new AuctionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        store_id = getArguments().getInt("store_id");
        adapter = new AuctionItemAdapter(getActivity(), mdata, R.layout.item_auction);
        listview.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, String> map = new HashMap<>();
                map.put("store_id", store_id + "");
                map.put("page", pageNo + "");
                map.put("pageSize", pageSize + "");
                presenter.getAllJiashang(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("store_id", store_id + "");
                    map.put("page", pageNo + "");
                    map.put("pageSize", pageSize + "");
                    presenter.getAllJiashang(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AuctionPresenter();
        return presenter;
    }

    @Override
    public void loadData() {
        HashMap<String, String> auctionMap = new HashMap<>();
        auctionMap.put("store_id", store_id + "");
        auctionMap.put("page", "1");
        auctionMap.put("pageSize", "10");
        presenter.getAllJiashang(pageNo, auctionMap);
        showLoading("");
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void refresh(ResultInfo<List<JiashangItem>> data) {
        isLoadMore(data.getData());
        mdata.clear();
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<JiashangItem>> data) {
        isLoadMore(data.getData());
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String msg) {
        ToastTools.showShort(getActivity(),msg);
    }

    private void isLoadMore(List<JiashangItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

}
