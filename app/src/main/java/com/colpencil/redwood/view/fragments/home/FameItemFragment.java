package com.colpencil.redwood.view.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.DynamicInfo;
import com.colpencil.redwood.bean.FameInfo;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.present.mine.DynamicPresent;
import com.colpencil.redwood.view.adapters.FameAdapter;
import com.colpencil.redwood.view.impl.IDynamicView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_fame_list
)
public class FameItemFragment extends ColpencilFragment implements IDynamicView {
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview)
    GridView gridview;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private FameAdapter adapter;
    private List<DynamicInfo> mlist = new ArrayList<>();
    private DynamicPresent dynamicPresent;
    private int type;

    public static FameItemFragment newInstance(int type){
        FameItemFragment fameItemFragment=new FameItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fameItemFragment.setArguments(bundle);
        return fameItemFragment;
    }

    @Override
    protected void initViews(View view) {
        type=getArguments().getInt("type");
        Log.d("pretty",type+"");


        refreshLayout.setDelegate(new BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String,Integer> intparams=new HashMap<>();
                intparams.put("cat_id",type);
                intparams.put("page",pageNo);
                intparams.put("pageSize",pageSize);
                HashMap<String, RequestBody> strparams=new HashMap<>();
                strparams.put("type",RequestBody.create(null,"pinpai"));
                showLoading("加载中...");
                dynamicPresent.getDynamic(pageNo,intparams,strparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String,Integer> intparams=new HashMap<>();
                    intparams.put("cat_id",type);
                    intparams.put("page",pageNo);
                    intparams.put("pageSize",pageSize);
                    HashMap<String, RequestBody> strparams=new HashMap<>();
                    strparams.put("type",RequestBody.create(null,"pinpai"));
                    showLoading("加载中...");
                    dynamicPresent.getDynamic(pageNo,intparams,strparams);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter=new FameAdapter(getActivity(),mlist,R.layout.item_brand_good);
        gridview.setAdapter(adapter);
        final HashMap<String,Integer> intparams=new HashMap<>();
        intparams.put("cat_id",type);
        intparams.put("page",pageNo);
        intparams.put("pageSize",pageSize);
        final HashMap<String, RequestBody> strparams=new HashMap<>();
        strparams.put("type",RequestBody.create(null,"pinpai"));
        showLoading("加载中...");
        dynamicPresent.getDynamic(pageNo,intparams,strparams);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        dynamicPresent=new DynamicPresent();
        return dynamicPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    private void isLoadMore(List<DynamicInfo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void loadMore(DynamicResult dynamicResult) {
        isLoadMore(dynamicResult.getData());
        mlist.addAll(dynamicResult.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(DynamicResult dynamicResult) {
        isLoadMore(dynamicResult.getData());
        mlist.clear();
        mlist.addAll(dynamicResult.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

}
