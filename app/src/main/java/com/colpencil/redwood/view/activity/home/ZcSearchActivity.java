package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.result.AllSpecialResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.mine.AllSpecialItemPresent;
import com.colpencil.redwood.view.activity.mine.SpecialActivity;

import com.colpencil.redwood.view.adapters.ZcListAdapter;
import com.colpencil.redwood.view.impl.AllSpecialItemView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;

import static com.colpencil.redwood.view.activity.HomeActivity.result;


/**
 * 首页专场搜索
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search_store)
public class ZcSearchActivity extends ColpencilActivity implements AllSpecialItemView {

    @Bind(R.id.base_header_edit)
    EditText editText;
    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    @Bind(R.id.search_store_list)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private int pageNo = 1;
    private String keyword;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private AllSpecialItemPresent allSpecialItemPresent;
    private List<AllSpecialInfo> cateList = new ArrayList<>();
    private ZcListAdapter mAdapter;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        keyword = getIntent().getStringExtra("keyword");
        editText.setHint("搜索专场");
        editText.setText(keyword);
        delegate = new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, RequestBody> strparams = new HashMap<>();
                strparams.put("keyword", RequestBody.create(null, keyword));
                HashMap<String, Integer> intparams = new HashMap<>();
                intparams.put("page", pageNo);
                intparams.put("pageSize", pageSize);
                allSpecialItemPresent.getSpecial(pageNo, strparams, intparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if(isRefresh){
                    pageNo++;
                    HashMap<String, RequestBody> strparams = new HashMap<>();
                    strparams.put("keyword", RequestBody.create(null, keyword));
                    HashMap<String, Integer> intparams = new HashMap<>();
                    intparams.put("page", pageNo);
                    intparams.put("pageSize", pageSize);
                    allSpecialItemPresent.getSpecial(pageNo, strparams, intparams);
                }
                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout2.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        initData();
        mAdapter = new ZcListAdapter(this, cateList, R.layout.item_special_past_child);
        initAdapter();
        listView.setAdapter(mAdapter);
    }

    private void initData() {
        showLoading(Constants.progressName);
        HashMap<String, RequestBody> strparams = new HashMap<>();
        strparams.put("keyword", RequestBody.create(null, keyword));
        HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("page", pageNo);
        intparams.put("pageSize", pageSize);
        allSpecialItemPresent.getSpecial(pageNo, strparams, intparams);
    }

    @OnClick(R.id.base_header_search)
    void search() {
        keyword = editText.getText().toString();
        loadData();
    }

    private void loadData() {
        showLoading(Constants.progressName);
        HashMap<String, RequestBody> strparams = new HashMap<>();
        strparams.put("keyword", RequestBody.create(null, keyword));
        HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("page", 1);
        intparams.put("pageSize", pageSize);
        allSpecialItemPresent.getSpecial(1, strparams, intparams);
        //保存搜索结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(keyword)) {
                    DaoUtils.saveHistory(10, keyword, ZcSearchActivity.this);
                }
            }
        }).start();
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    private void initAdapter() {
        mAdapter.setComListener(new ZcListAdapter.comItemClickListener() {
            @Override
            public void click(String id, String name,Integer cat_id) {
                Intent intent = new Intent(ZcSearchActivity.this,SpecialActivity.class);
                intent.putExtra("special_id", id);
                intent.putExtra("special_name", name);
                intent.putExtra("cat_id",cat_id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {
        hideLoading();
        ToastTools.showShort(this, message);
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
        refreshLayout2.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }


    @Override
    public ColpencilPresenter getPresenter() {
        allSpecialItemPresent = new AllSpecialItemPresent();
        return allSpecialItemPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

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
    public void loadMore(AllSpecialResult allSpecialResult) {
        isLoadMore(allSpecialResult.getData());
        cateList.addAll(allSpecialResult.getData());
        mAdapter.notifyDataSetChanged();
        hideLoading();
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
}
