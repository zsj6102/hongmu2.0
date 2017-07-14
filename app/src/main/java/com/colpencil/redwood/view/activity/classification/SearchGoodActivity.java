package com.colpencil.redwood.view.activity.classification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.cyclopedia.HotSearchPresenter;
import com.colpencil.redwood.view.activity.home.GoodResultActivity;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.adapters.SearchGoodAdapter;
import com.colpencil.redwood.view.impl.ISearchView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:商品搜索
 * @Email DramaScript@outlook.com
 * @date 2016/8/22
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_search_article
)
public class SearchGoodActivity extends ColpencilActivity implements ISearchView {

    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    @Bind(R.id.search_article_listview)
    ListView listView;
    @Bind(R.id.base_header_edit)
    EditText edit;
    private HotSearchPresenter presenter;
    private int cat_id = 7;
    private HeaderViewHolder holder;
    private List<String> historylist = new ArrayList<>();
    private List<String> hotlist = new ArrayList<>();
    private SearchGoodAdapter hisadapter;
    private SearchGoodAdapter hotadapter;
    private View header;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.main_brown));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        edit.setHint("搜索商品");
        initAdapter();
        presenter.loadHot(cat_id);
    }

    private void initAdapter() {
        header = LayoutInflater.from(this).inflate(R.layout.activity_search_header, null);
        holder = new HeaderViewHolder(header);
        listView.addHeaderView(header);
        listView.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        hisadapter = new SearchGoodAdapter(this, historylist, R.layout.item_category);
        holder.ll_type.setVisibility(View.GONE);
        holder.history_gridview.setAdapter(hisadapter);
        hotadapter = new SearchGoodAdapter(this, hotlist, R.layout.item_category);
        holder.hot_gridview.setAdapter(hotadapter);
        holder.history_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent(0, historylist.get(position));
            }
        });
        holder.hot_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent(1, hotlist.get(position));
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HotSearchPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    private void intent(int type, String key) {
        Intent intent = new Intent();
        intent.putExtra("keyword", key);
        intent.setClass(this, GoodResultActivity.class);
        startActivity(intent);
        if (type == 2) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DaoUtils.saveHistory(cat_id, edit.getText().toString(), SearchGoodActivity.this);
                }
            }).start();
        }
    }

    @Override
    public void hot(List<String> list) {
        Log.e("list", list.size() + "");
        hotlist.clear();
        hotlist.addAll(list);
        hotadapter.notifyDataSetChanged();
    }

    @Override
    public void history(List<String> list) {
        Log.e("list", list.size() + "");
        historylist.clear();
        historylist.addAll(getListSplit(list));
        hisadapter.notifyDataSetChanged();
    }

    /**
     * 获取前12个元素
     *
     * @param list
     * @return
     */
    public List<String> getListSplit(List<String> list) {
        List<String> slist = new ArrayList<>();
        if (!ListUtils.listIsNullOrEmpty(list)) {
            for (int i = list.size() - 1; i >= 0; i--) {
                slist.add(list.get(i));
            }
        }
        return slist;
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.base_header_search)
    void search() {
        intent(2, edit.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadHistory(cat_id, this);
    }

    class HeaderViewHolder {

        @Bind(R.id.search_header_type)
        LinearLayout ll_type;
        @Bind(R.id.search_article_history_gridview)
        MosaicGridView history_gridview;
        @Bind(R.id.search_article_hot_gridview)
        MosaicGridView hot_gridview;

        public HeaderViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.search_history_clear)
        void clearClick() {
            historylist.clear();
            hisadapter.notifyDataSetChanged();
            DaoUtils.deleteHistory(cat_id, SearchGoodActivity.this);
        }
    }
}
