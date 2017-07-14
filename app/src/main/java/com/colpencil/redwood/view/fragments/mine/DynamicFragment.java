package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.DynamicInfo;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.present.mine.DynamicPresent;
import com.colpencil.redwood.view.adapters.DynamicAdapter;
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

import static android.R.attr.type;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_gridview
)
public class DynamicFragment extends ColpencilFragment implements IDynamicView {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview)
    GridView gridview;

    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private DynamicAdapter mAdapter;
    private List<DynamicInfo> mlist = new ArrayList<>();
    private DynamicPresent dynamicPresent;
    private int type;

    public static DynamicFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        DynamicFragment fragment = new DynamicFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type=getArguments().getInt("type");
        Log.d("pretty",type+"");
        final HashMap<String,Integer> intparams=new HashMap<>();
        intparams.put("cat_id",type);
        intparams.put("page",pageNo);
        intparams.put("pageSize",pageSize);
        final HashMap<String, RequestBody> strparams=new HashMap<>();
        strparams.put("type",RequestBody.create(null,"pinpai"));

        refreshLayout.setDelegate(new BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                showLoading("加载中...");
                dynamicPresent.getDynamic(pageNo,intparams,strparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
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
        mAdapter = new DynamicAdapter(getActivity(), mlist, R.layout.item_brand_good);
        gridview.setAdapter(mAdapter);

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
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(DynamicResult dynamicResult) {
        isLoadMore(dynamicResult.getData());
        mlist.clear();
        mlist.addAll(dynamicResult.getData());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

}
