package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllAuctionItemInfo;
import com.colpencil.redwood.view.adapters.AllAuctionItemAdapter;
import com.colpencil.redwood.view.adapters.DynamicAdapter;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_auctionitem
)
public class AuctionItemFragment extends ColpencilFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview)
    GridView gridview;
    private int page = 1, pageSize = 10;
    private boolean isRefresh = false;
    private AllAuctionItemAdapter adapter;
    private List<AllAuctionItemInfo> allAuctionItemInfos=new ArrayList<>();

    @Override
    protected void initViews(View view) {
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new AllAuctionItemAdapter(getActivity(), allAuctionItemInfos, R.layout.item_brand_good);
        gridview.setAdapter(adapter);
        loadData();

    }

//    private void loadData() {
//        for(int i=0;i<8;i++){
//            AllAuctionItemInfo allAuctionItemInfo=new AllAuctionItemInfo();
//            allAuctionItemInfos.add(allAuctionItemInfo);
//        }
//        adapter.notifyDataSetChanged();
//        refreshLayout.endRefreshing(0);
//    }
@Override
public void loadData() {

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
}
