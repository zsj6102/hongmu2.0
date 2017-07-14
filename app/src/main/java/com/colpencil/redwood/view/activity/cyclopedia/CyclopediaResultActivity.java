package com.colpencil.redwood.view.activity.cyclopedia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.cyclopedia.SearchCycloPresenter;
import com.colpencil.redwood.view.adapters.ItemSearchCycloAdapter;
import com.colpencil.redwood.view.impl.ISearchCycloView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:搜索百科结果
 * @Email DramaScript@outlook.com
 * @date 2016/8/4
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search_cyclopedia)
public class CyclopediaResultActivity extends ColpencilActivity implements ISearchCycloView, BGARefreshLayoutDelegate {

    @Bind(R.id.search_listview)
    ListView gridView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.base_header_edit)
    EditText edit;
    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    private SearchCycloPresenter presenter;
    private ItemSearchCycloAdapter adapter;
    private List<CyclopediaInfoVo> cylist = new ArrayList<>();
    private String keyword;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        keyword = getIntent().getStringExtra("keyword");
        edit.setText(keyword);
        edit.setHint("搜索文章");
        adapter = new ItemSearchCycloAdapter(this, cylist, R.layout.item_second_cyclopedia);
        gridView.setAdapter(adapter);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        presenter.loadCycloList(3, keyword, page, pageSize);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CycloParams params = new CycloParams();
                Intent intent = new Intent(CyclopediaResultActivity.this, CyclopediaDetailActivity.class);
                params.article_id = adapter.getItem(position).getArticle_id() + "";
                params.type = "cyclopedia";
                params.title = adapter.getItem(position).getPage_title();
                params.content = adapter.getItem(position).getPage_description();
                params.image = adapter.getItem(position).getImage();
                params.time = adapter.getItem(position).getAdd_time();
                intent.putExtra("params", params);
                startActivity(intent);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SearchCycloPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess(List<CyclopediaInfoVo> cyclopediaInfoVos) {
        if (ListUtils.listIsNullOrEmpty(cyclopediaInfoVos)) {
            isRefresh = false;
        }
        hideLoading();
        isLoadMore(cyclopediaInfoVos);
        refreshLayout.endRefreshing(cyclopediaInfoVos.size());
        cylist.addAll(cyclopediaInfoVos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError() {
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.base_header_search)
    void submit() {
        load();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        cylist.clear();
        presenter.loadCycloList(3, keyword, page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadCycloList(3, keyword, page, pageSize);
        }
        return false;
    }

    private void load() {
        showLoading(Constants.progressName);
        page = 1;
        keyword = edit.getText().toString();
        presenter.loadCycloList(3, keyword, page, pageSize);
        //保存搜索结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(edit.getText().toString())) {
                    DaoUtils.saveHistory(6, edit.getText().toString(), CyclopediaResultActivity.this);
                }
            }
        }).start();
    }

    private void isLoadMore(List<CyclopediaInfoVo> cyclopediaInfoVos) {
        if (page == 1) {
            cylist.clear();
        }
        if (cyclopediaInfoVos.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
    }
}
