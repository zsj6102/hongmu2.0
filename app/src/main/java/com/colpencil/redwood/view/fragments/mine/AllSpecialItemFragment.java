package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.holder.SpecialCategory;
import com.colpencil.redwood.holder.adapter.SeparatedListAdapter;
import com.colpencil.redwood.present.mine.AllSpecialItemPresent;
import com.colpencil.redwood.view.activity.mine.SpecialActivity;
import com.colpencil.redwood.view.adapters.ZcListAdapter;
import com.colpencil.redwood.view.impl.AllSpecialItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import butterknife.Bind;
import okhttp3.RequestBody;

import static com.colpencil.redwood.view.activity.HomeActivity.result;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_allspecial)
public class AllSpecialItemFragment extends ColpencilFragment implements AllSpecialItemView {
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    @Bind(R.id.listview)
    ListView listView;
    private int type;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private AllSpecialItemPresent allSpecialItemPresent;
    private List<AllSpecialInfo> cateList = new ArrayList<>();
    private ZcListAdapter mAdapter;

    public static AllSpecialItemFragment newInstance(int type) {
        AllSpecialItemFragment allSpecialItemFragment = new AllSpecialItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        allSpecialItemFragment.setArguments(bundle);
        return allSpecialItemFragment;
    }

    @Override
    protected void initViews(View view) {

        type = getArguments().getInt("type");
        Log.d("pretty", type + "");
        final HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("cat_id", type);
        intparams.put("page", pageNo);
        intparams.put("pageSize", pageSize);
        final HashMap<String, RequestBody> strparams = new HashMap<>();
        mAdapter = new ZcListAdapter(getActivity(), cateList,R.layout.item_special_past_child);
        listView.setAdapter(mAdapter);
        delegate = new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                allSpecialItemPresent.getSpecial(pageNo, strparams, intparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    allSpecialItemPresent.getSpecial(pageNo, strparams, intparams);
                }
                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout2.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));

        mAdapter.setComListener(new  ZcListAdapter.comItemClickListener() {
            @Override
            public void click(String id, String name,Integer cat_id) {
                Intent intent = new Intent(getActivity(), SpecialActivity.class);
                intent.putExtra("special_id", id);
                intent.putExtra("special_name", name);
                intent.putExtra("cat_id",cat_id);
                startActivity(intent);
            }
        });

    }

    @Override
    public ColpencilPresenter getPresenter() {
        allSpecialItemPresent = new AllSpecialItemPresent();
        return allSpecialItemPresent;
    }

    @Override
    public void loadData() {
        showLoading("加载中...");
        final HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("cat_id", type);
        intparams.put("page", pageNo);
        intparams.put("pageSize", pageSize);
        final HashMap<String, RequestBody> strparams = new HashMap<>();
        allSpecialItemPresent.getSpecial(pageNo, strparams, intparams);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void isLoadMore(List<AllSpecialInfo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
    }

    @Override
    public void loadMore(AllSpecialResult allSpecialResult) {
        isLoadMore(allSpecialResult.getData());
        cateList.addAll(allSpecialResult.getData()) ;
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    //去除重复的元素并保持原顺序
//    private void removeDuplicateWithOrder(List list) {
//        Set set = new HashSet();
//        List newList = new ArrayList();
//        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
//            Object element = iter.next();
//            if (set.add(element))
//                newList.add(element);
//        }
//        list.clear();
//        list.addAll(newList);
//    }
//
//    private List<SpecialCategory> out(List<AllSpecialInfo> mlist, List<String> longlist) {
//         List<SpecialCategory> mylist = new ArrayList<>();
//        for (int m = 0; m < mlist.size(); m++) {
//            //冒泡排序之后
//            for (int j = m + 1; j < mlist.size(); j++) {
//                if (mlist.get(j).getCreate_time() > mlist.get(m).getCreate_time()) {
//                    AllSpecialInfo temp = mlist.get(m);
//                    mlist.set(m, mlist.get(j));
//                    mlist.set(j, temp);
//                }
//            }
//        }
//        for (int m = 0; m < mlist.size(); m++) {
//            longlist.add(TimeUtil.longToStringYear(mlist.get(m).getCreate_time()));
//        }
//        if (longlist.size() != 0) {
//            removeDuplicateWithOrder(longlist);
//        }
//        for (int j = 0; j < longlist.size(); j++) {
//            SpecialCategory sp;
//            sp = new SpecialCategory(longlist.get(j));
//            for (int m = 0; m < mlist.size(); m++) {
//                if (TimeUtil.longToStringYear(mlist.get(m).getCreate_time()).equals(longlist.get(j))) {
//                    sp.addItem(mlist.get(m));
//                }
//            }
//            mylist.add(sp);
//        }
//        return mylist;
//    }

    @Override
    public void refresh(AllSpecialResult allSpecialResult) {
        isLoadMore(allSpecialResult.getData());
        cateList.clear();
        cateList.addAll(allSpecialResult.getData());
        mAdapter.notifyDataSetChanged();
        if (ListUtils.listIsNullOrEmpty(allSpecialResult.getData())) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    @Override
    public void loadSuccess() {
      hideLoading();
    }

    @Override
    public void loadFail(String message) {
        hideLoading();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
        refreshLayout2.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

}
