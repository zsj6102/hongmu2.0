package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.present.mine.SupaiPresenter;
import com.colpencil.redwood.view.adapters.ItemAllAuctionAdapter;
import com.colpencil.redwood.view.impl.ISupaiDynamic;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@ActivityFragmentInject(contentViewId = R.layout.fragment_listview)
public class SupaiDynamicFragment extends ColpencilFragment implements ISupaiDynamic {

    @Bind(R.id.supai_listview)
    ListView supaiListview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private SupaiPresenter presenter;
    private boolean isRefresh = false;
    private ItemAllAuctionAdapter adapter;
    private List<AllGoodsInfo> allGoodsInfoList = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private int type;
    public static SupaiDynamicFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        SupaiDynamicFragment fragment = new SupaiDynamicFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        final   HashMap<String,String> params = new HashMap<String, String>();
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("member_id",SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
        params.put("type","supai");
        params.put("page",page+"");
        params.put("pageSize",pageSize+"");

       refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
           @Override
           public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
               page = 1;
               if(type == 1){
                   presenter.getSupaiDynamic(page,params);
               }else{
                   presenter.getSupaiCol(page,params);
               }
           }

           @Override
           public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
               if (isRefresh) {
                   page++;
                   if(type == 1){
                       presenter.getSupaiDynamic(page,params);
                   }else{
                       presenter.getSupaiCol(page,params);
                   }
               }
               return false;
           }
       });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        adapter = new ItemAllAuctionAdapter(getActivity(), allGoodsInfoList, R.layout.item_allauctionitem);
        supaiListview.setAdapter(adapter);
    }
    @Override
    public void loadData() {
        showLoading("加载中...");
        final   HashMap<String,String> params = new HashMap<String, String>();
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("member_id",SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
        params.put("type","supai");
        params.put("page",page+"");
        params.put("pageSize",pageSize+"");
        if(type == 1){
            presenter.getSupaiDynamic(page,params);
        }else{
            presenter.getSupaiCol(page,params);
        }
    }
    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SupaiPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess() {
        hideLoading();
    }

    @Override
    public void loadFail(String message) {
        hideLoading();
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreSp(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refreshSp(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.clear();
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }
    private void isLoadMore(List<AllGoodsInfo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
