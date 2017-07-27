package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.bean.DynamicInfo;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.present.home.AllAuctionItemPresent;
import com.colpencil.redwood.view.adapters.ItemAllAuctionAdapter;
import com.colpencil.redwood.view.impl.AllAuctionItemView;
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
import okhttp3.RequestBody;

import static com.colpencil.redwood.view.activity.HomeActivity.result;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_allauctionitem)
public class AllAuctionItemFragment extends ColpencilFragment implements AllAuctionItemView {

    @Bind(R.id.allauctionitem_listview)
    ListView allauctionitem;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private boolean isRefresh = false;
    private ItemAllAuctionAdapter adapter;
    private List<AllGoodsInfo> allGoodsInfoList = new ArrayList<>();
    private AllAuctionItemPresent allAuctionItemPresent;
    private int page = 1;
    private int pageSize = 10;
    private int type;

    public static AllAuctionItemFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        AllAuctionItemFragment fragment = new AllAuctionItemFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                HashMap<String, RequestBody> strparams = new HashMap<>();
                strparams.put("type", RequestBody.create(null, "supai"));
                strparams.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(getActivity()).getString("token")));

                HashMap<String, Integer> intparams = new HashMap<>();
                intparams.put("page", page);
                intparams.put("pageSize", pageSize);
                intparams.put("cat_id", type);
                Log.d("pretty", type + "");
                showLoading("加载中...");
                allAuctionItemPresent.getAllGoods(page,strparams, intparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if(isRefresh){
                    page++;
                    HashMap<String, RequestBody> strparams = new HashMap<>();
                    strparams.put("type", RequestBody.create(null, "supai"));
                    strparams.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(getActivity()).getString("token")));

                    HashMap<String, Integer> intparams = new HashMap<>();
                    intparams.put("page", page);
                    intparams.put("pageSize", pageSize);
                    intparams.put("cat_id", type);
                    Log.d("pretty", type + "");
                    showLoading("加载中...");
                    allAuctionItemPresent.getAllGoods(page,strparams, intparams);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new ItemAllAuctionAdapter(getActivity(), allGoodsInfoList, R.layout.item_allauctionitem);
        allauctionitem.setAdapter(adapter);
        HashMap<String, RequestBody> strparams = new HashMap<>();
        strparams.put("type", RequestBody.create(null, "supai"));
        strparams.put("token", RequestBody.create(null, SharedPreferencesUtil.getInstance(getActivity()).getString("token")));

        HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("page", page);
        intparams.put("pageSize", pageSize);
        intparams.put("cat_id", type);
        Log.d("pretty", type + "");
        showLoading("加载中...");
        allAuctionItemPresent.getAllGoods(page,strparams, intparams);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        allAuctionItemPresent = new AllAuctionItemPresent();
        return allAuctionItemPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getAllGoods(AllGoodsResult allGoodsResult) {
        List<AllGoodsInfo> list = allGoodsResult.getData();
        allGoodsInfoList.addAll(list);
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(AllGoodsResult result) {
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
