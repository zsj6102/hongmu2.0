package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.DynamicInfo;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.mine.DynamicPresent;
import com.colpencil.redwood.view.adapters.DynamicAdapter;
import com.colpencil.redwood.view.impl.IDynamicView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;

import static android.R.attr.type;
import static com.colpencil.redwood.view.activity.HomeActivity.result;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_gridview)
public class DynamicFragment extends ColpencilFragment implements IDynamicView {
    /**
     * 名师品牌公用
     */
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview)
    GridView gridview;
    //    @Bind(R.id.no_data_layout)
    //    LinearLayout nodataLayout;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private DynamicAdapter mAdapter;
    private List<DynamicInfo> mlist = new ArrayList<>();
    private DynamicPresent dynamicPresent;
    private int type;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    public static DynamicFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        DynamicFragment fragment = new DynamicFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        Log.d("pretty", type + "");
        //        final HashMap<String,Integer> intparams=new HashMap<>();
        //        intparams.put("cat_id",type);
        //        intparams.put("page",pageNo);
        //        intparams.put("pageSize",pageSize);
        //        final HashMap<String, RequestBody> strparams=new HashMap<>();
        //                strparams.put("type",RequestBody.create(null,"pinpai"));
        final HashMap<String, String> intparams = new HashMap<>();
        intparams.put("page", pageNo + "");
        intparams.put("pageSize", pageSize + "");
        if (type == 2 || type == 21) {
            intparams.put("type", "pinpai");
        } else if (type == 3 || type == 31) {
            intparams.put("type", "mingjiang");
        }

        intparams.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        intparams.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));

        delegate = new BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                showLoading("加载中...");
                if (type == 2 || type == 3) {
                    dynamicPresent.getStoreDynamic(pageNo, intparams);
                } else {
                    dynamicPresent.getCollection(pageNo, intparams);
                }
                //                dynamicPresent.getDynamic(pageNo, intparams );
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    showLoading("加载中...");
                    if (type == 0) {
                        dynamicPresent.getStoreDynamic(pageNo, intparams);
                    } else {
                        dynamicPresent.getCollection(pageNo, intparams);
                    }
                    //                    dynamicPresent.getDynamic(pageNo, intparams );
                }
                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout2.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        mAdapter = new DynamicAdapter(getActivity(), mlist, R.layout.item_brand_good);
        gridview.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {
        showLoading("加载中...");
        final HashMap<String, String> intparams = new HashMap<>();
        intparams.put("page", pageNo + "");
        intparams.put("pageSize", pageSize + "");
        if (type == 2 || type == 21) {
            intparams.put("type", "pinpai");
        } else if (type == 3 || type == 31) {
            intparams.put("type", "mingjiang");
        }

        intparams.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        intparams.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        if (type == 2 || type == 3) {
            dynamicPresent.getStoreDynamic(pageNo, intparams);
        } else {
            dynamicPresent.getCollection(pageNo, intparams);
        }

    }

    @Override
    public ColpencilPresenter getPresenter() {
        dynamicPresent = new DynamicPresent();
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
        refreshLayout2.endRefreshing(0);
        refreshLayout2.endLoadingMore();
        refreshLayout2.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {
        hideLoading();
        ToastTools.showShort(getActivity(),message);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endRefreshing(0);
        refreshLayout2.endLoadingMore();
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
        if (ListUtils.listIsNullOrEmpty(dynamicResult.getData())) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

}
