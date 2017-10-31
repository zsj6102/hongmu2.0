package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.EncyclopediasInfo;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.present.mine.EncycloPresenter;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.adapters.EncyclopediasAdapter;
import com.colpencil.redwood.view.impl.IEncycloView;
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

import static android.R.attr.data;
import static com.alipay.sdk.packet.impl.c.t;
import static com.colpencil.redwood.holder.HolderFactory.map;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_encyclopedias)
public class EncyclopediasFragment extends ColpencilFragment implements IEncycloView {
    @Bind(R.id.encyclopedias_listview)
    ListView encyclopedias_listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private EncycloPresenter presenter;
    private List<ArticalItem> mdata = new ArrayList<>();
    private int store_id;
    private int cat_id;
    private int pageNo = 1, pageSize = 10;
    private EncyclopediasAdapter adapter;
    private boolean isRefresh = false;
    public static EncyclopediasFragment newInstance(int store_id, int cat_id) {
        EncyclopediasFragment fragment = new EncyclopediasFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        bundle.putInt("cat_id", cat_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        store_id = getArguments().getInt("store_id");
        cat_id = getArguments().getInt("cat_id");
        adapter = new EncyclopediasAdapter(getActivity(),mdata,R.layout.item_encyclopedias);
        encyclopedias_listview.setAdapter(adapter);
        if(cat_id == 3){
            encyclopedias_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CycloParams params = new CycloParams();
                    Intent intent = new Intent(getActivity(), CyclopediaDetailActivity.class);
                    params.article_id = mdata.get(position).getH_id() + "";
                    params.type = "cyclopedia";
                    params.title = mdata.get(position).getH_title();
                    params.content = "";
                    params.image = mdata.get(position).getH_img();
                    params.time = mdata.get(position).getCreate_time();
                    intent.putExtra("params", params);
                    startActivity(intent);
                }
            });
        }else if(cat_id == 8){
            encyclopedias_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CycloParams params = new CycloParams();
                    Intent intent = new Intent(getActivity(), CyclopediaDetailActivity.class);
                    params.article_id = mdata.get(position).getH_id() + "";
                    params.type = "news";
                    params.title = mdata.get(position).getH_title();
                    params.content = "";
                    params.image = mdata.get(position).getH_img();
                    params.time = mdata.get(position).getCreate_time();
                    intent.putExtra("params", params);
                    startActivity(intent);
                }
            });
        }
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, String> map = new HashMap<>();
                map.put("store_id", store_id + "");
                map.put("page", pageNo + "");
                map.put("cat_id",cat_id+"");
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                map.put("pageSize", pageSize + "");
                presenter.getArticalList(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("store_id", store_id + "");
                    map.put("page", pageNo + "");
                    map.put("cat_id",cat_id+"");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                    map.put("pageSize", pageSize + "");
                    presenter.getArticalList(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));



    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new EncycloPresenter();
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
        auctionMap.put("cat_id",cat_id+"");
        auctionMap.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
        auctionMap.put("pageSize", pageSize + "");
        presenter.getArticalList(pageNo, auctionMap);
        showLoading("");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void refresh(ResultInfo<List<ArticalItem>> result) {
        isLoadMore(result.getData());
        mdata.clear();
        mdata.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<ArticalItem>> result) {
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
    private void isLoadMore(List<ArticalItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
}
