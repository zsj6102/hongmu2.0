package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.SpecialPastItem;
import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.colpencil.redwood.present.mine.AllSpecialItemPresent;
import com.colpencil.redwood.view.activity.mine.SpecialActivity;
import com.colpencil.redwood.view.adapters.SpecialPastAdapter;
import com.colpencil.redwood.view.impl.AllSpecialItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_allspecial
)
public class AllSpecialItemFragment extends ColpencilFragment implements AllSpecialItemView {
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.expandableList)
    ExpandableListView expand_list;

    private int type;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private AllSpecialItemPresent allSpecialItemPresent;
    private List<SpecialPastItem> list = new ArrayList<>();
    private SpecialPastAdapter adapter;

    public static AllSpecialItemFragment newInstance(int type){
        AllSpecialItemFragment allSpecialItemFragment=new AllSpecialItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        allSpecialItemFragment.setArguments(bundle);
        return allSpecialItemFragment;
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

        expand_list.setIndicatorBounds(5,25);
        expand_list.setDivider(null);
        expand_list.setGroupIndicator(null);
        adapter = new SpecialPastAdapter(getActivity(), list);
        expand_list.setAdapter(adapter);
        expand_list.setItemsCanFocus(true);
        expand_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                showLoading("加载中...");
                allSpecialItemPresent.getSpecial(pageNo,strparams,intparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    showLoading("加载中...");
                    allSpecialItemPresent.getSpecial(pageNo,strparams,intparams);
                }
                return false;
            }
        });

        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));

        showLoading("加载中...");
        allSpecialItemPresent.getSpecial(pageNo,strparams,intparams);
        expand_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                SpecialPastItem info=list.get(i);
                Intent intent=new Intent(getActivity(), SpecialActivity.class);
                intent.putExtra("special_id",info.getSpecial_id());
                intent.putExtra("special_name",info.getSpecial_name());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        allSpecialItemPresent=new AllSpecialItemPresent();
        return allSpecialItemPresent;
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
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
    @Override
    public void loadMore(AllSpecialResult allSpecialResult) {
        isLoadMore(allSpecialResult.getData());
        List<AllSpecialInfo> specialInfoList=allSpecialResult.getData();
        for(int i=0;i<specialInfoList.size();i++){
            SpecialPastItem specialPastItem=new SpecialPastItem();
            specialPastItem.setTime(TimeUtil.longToStringYear(specialInfoList.get(i).getCreate_time()));
            specialPastItem.setSpecial_id(specialInfoList.get(i).getSpecial_id());
            specialPastItem.setSpecial_name(specialInfoList.get(i).getSpecial_name());
            List<String> listimg=new ArrayList<>();
            listimg.add(specialInfoList.get(i).getSpe_picture());
            specialPastItem.setImgUrl(listimg);
            list.add(i,specialPastItem);
            expand_list.expandGroup(i);
        }
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(AllSpecialResult allSpecialResult) {
        isLoadMore(allSpecialResult.getData());
        list.clear();
        List<AllSpecialInfo> specialInfoList=allSpecialResult.getData();
        for(int i=0;i<specialInfoList.size();i++){
            SpecialPastItem specialPastItem=new SpecialPastItem();
            specialPastItem.setTime(TimeUtil.longToStringYear(specialInfoList.get(i).getCreate_time()));
            specialPastItem.setSpecial_id(specialInfoList.get(i).getSpecial_id());
            specialPastItem.setSpecial_name(specialInfoList.get(i).getSpecial_name());
            List<String> listimg=new ArrayList<>();
            listimg.add(specialInfoList.get(i).getSpe_picture());
            specialPastItem.setImgUrl(listimg);
            list.add(i,specialPastItem);
            expand_list.expandGroup(i);
        }
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {

    }

}
