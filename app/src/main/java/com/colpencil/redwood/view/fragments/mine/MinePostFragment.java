package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.MinePostInfo;
import com.colpencil.redwood.bean.MinePostItem;
import com.colpencil.redwood.bean.PostItemInfo;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.present.mine.MinePostPresenter;
import com.colpencil.redwood.view.adapters.MinePostAdapter;
import com.colpencil.redwood.view.impl.IMinePostView;
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

import static com.colpencil.redwood.holder.HolderFactory.map;

/**
 * @author QFZ
 * @Description:他的帖子
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_minepost)
public class MinePostFragment extends ColpencilFragment implements IMinePostView {
    @Bind(R.id.minepost_listview)
    ListView minepost_listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private int store_id;
    private MinePostPresenter presenter;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private List<MinePostItem> mdata = new ArrayList<>();
    private MinePostAdapter adapter;

    public static MinePostFragment newInstance(int store_id) {
        MinePostFragment fragment = new MinePostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        store_id = getArguments().getInt("store_id");
        adapter = new MinePostAdapter(getActivity(), mdata, R.layout.item_minepost);
        minepost_listview.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, String> map = new HashMap<>();
                map.put("store_id", store_id + "");
                map.put("page", pageNo + "");
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                map.put("pageSize", pageSize + "");
                presenter.getMinePost(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("store_id", store_id + "");
                    map.put("page", pageNo + "");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                    map.put("pageSize", pageSize + "");
                    presenter.getMinePost(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));

    }

    @Override
    public void loadData() {
        HashMap<String, String> auctionMap = new HashMap<>();
        auctionMap.put("store_id", store_id + "");
        auctionMap.put("page", pageNo + "");
        auctionMap.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
        auctionMap.put("pageSize", pageSize + "");
        presenter.getMinePost(pageNo, auctionMap);
        showLoading("");
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MinePostPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void refresh(ResultInfo<List<MinePostItem>> data) {
        isLoadMore(data.getData());
        mdata.clear();
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<MinePostItem>> data) {
        isLoadMore(data.getData());
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String msg) {
        ToastTools.showShort(getActivity(), msg);
    }

    private void isLoadMore(List<MinePostItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
}
