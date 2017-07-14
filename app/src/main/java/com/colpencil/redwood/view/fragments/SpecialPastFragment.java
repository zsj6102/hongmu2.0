package com.colpencil.redwood.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.SpecialPastItem;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.view.adapters.SpecialPastAdapter;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author YangWeiJie
 * Created by Administrator on 2017/2/20.
 */

@ActivityFragmentInject(contentViewId = R.layout.fragment_special_past)
public class SpecialPastFragment extends ColpencilFragment implements BGARefreshLayout.BGARefreshLayoutDelegate{

    @Bind(R.id.base_header_edit)
    EditText edit_search;

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;

    @Bind(R.id.expandableList)
    ExpandableListView expand_list;

    private String keyWord;
    private List<SpecialPastItem> list = new ArrayList<>();
    private SpecialPastAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    public static SpecialPastFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        SpecialPastFragment fragment = new SpecialPastFragment();
        bundle.putSerializable("special_id", id);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initViews(View view) {
        //测试数据
        SpecialPastItem item1 = new SpecialPastItem();
        item1.setTime("2016-02-22");
        List<String> list1 = new ArrayList<>();
        list1.add(0,"https://www.baidu.com/img/bd_logo1.png");
        list1.add(1,"https://www.baidu.com/img/bd_logo1.png");
        item1.setImgUrl(list1);
        SpecialPastItem item2 = new SpecialPastItem();
        item2.setTime("2017-01-29");
        List<String> list2 = new ArrayList<>();
        list2.add(0,"https://www.baidu.com/img/bd_logo1.png");
        item2.setImgUrl(list2);
        list.add(0,item1);
        list.add(1,item2);


        expand_list.setIndicatorBounds(5,25);
        expand_list.setDivider(null);
        expand_list.setGroupIndicator(null);
        adapter = new SpecialPastAdapter(getActivity(), list);
        expand_list.setAdapter(adapter);
        expand_list.setItemsCanFocus(true);
        expand_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(),true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        //测试
        for(int i = 0; i < adapter.getGroupCount(); i++){
            expand_list.expandGroup(i);
        }
        expand_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.base_header_search)
    void submit(){
        loadData();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        list.clear();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if(isRefresh){
            page++;
        }
        return false;
    }

    private void loadData(){
        showLoading(Constants.progressName);
        page = 1;
        keyWord = edit_search.getText().toString();

    }

    private void isLoadMore(List<SpecialPastItem> itemList){
        if(page == 1){
            list.clear();
        }
        if(itemList.size() < pageSize){
            isRefresh = false;
        } else {
            isRefresh = true;
        }
    }
}
