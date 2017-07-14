package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.present.cyclopedia.HotSearchPresenter;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaResultActivity;
import com.colpencil.redwood.view.adapters.HotSearchAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.adapters.SearchHistoryAdapter;
import com.colpencil.redwood.view.impl.ISearchView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description: 搜索文章的Activity
 * @Email DramaScript@outlook.com
 * @date 2016/7/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_search_article
)
public class SearchActivity extends ColpencilActivity implements ISearchView {

    @Bind(R.id.search_all_header)
    LinearLayout all_header;
    @Bind(R.id.base_header_edit)
    EditText edit;
    @Bind(R.id.search_article_listview)
    ListView listView;
    private View ll_header;
    private int cat_id = 7;

    private HeaderViewHolder holder;
    private List<String> historylist = new ArrayList<>();
    private List<String> hotlist = new ArrayList<>();
    private SearchHistoryAdapter hisadapter;
    private HotSearchAdapter hotadapter;

    private HotSearchPresenter presenter;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        all_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        edit.setHint("搜索商品");
        initAdapter();
        presenter.loadHot(cat_id);
    }

    private void initAdapter() {
        ll_header = LayoutInflater.from(this).inflate(R.layout.activity_search_header, null);
        holder = new HeaderViewHolder(ll_header);
        listView.addHeaderView(ll_header);
        listView.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
        hisadapter = new SearchHistoryAdapter(this, historylist, R.layout.item_search_history_noborder);
        holder.history_gridview.setAdapter(hisadapter);
        hotadapter = new HotSearchAdapter(this, hotlist, R.layout.item_search_history);
        holder.hot_gridview.setAdapter(hotadapter);
        holder.history_gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent(0, position);
            }
        });
        holder.hot_gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent(1, position);
            }
        });
        holder.ll_type.setVisibility(View.VISIBLE);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HotSearchPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.unbind();
    }

    @Override
    public void hot(List<String> list) {
        hotlist.clear();
        hotlist.addAll(list);
        hotadapter.notifyDataSetChanged();
    }

    @Override
    public void history(List<String> list) {
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
            ListIterator<String> iterator = list.listIterator(list.size());
            while (slist.size() < 12 && iterator.hasPrevious()) {
                slist.add(iterator.previous());
            }
        }
        return slist;
    }

    @OnClick(R.id.base_header_search)
    void searchOnClick() {
        intent(2, 0);
    }

    private void intent(int type, int position) {
        Intent intent = new Intent();
        if (type == 0) {
            intent.putExtra("keyword", historylist.get(position));
        } else if (type == 1) {
            intent.putExtra("keyword", hotlist.get(position));
        } else {
            intent.putExtra("keyword", edit.getText().toString());
        }
        if (cat_id == 6) {
            intent.setClass(this, CyclopediaResultActivity.class);
        } else if (cat_id == 7) {
            intent.setClass(this, GoodResultActivity.class);
        } else {
            intent.setClass(this, PostsResultActivity.class);
        }
        startActivity(intent);
        if (type == 2) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(edit.getText().toString())) {
                        DaoUtils.saveHistory(cat_id, edit.getText().toString(), SearchActivity.this);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadHistory(cat_id, SearchActivity.this);
    }

    class HeaderViewHolder {

        @Bind(R.id.search_article_history_gridview)
        MosaicGridView history_gridview;
        @Bind(R.id.search_article_hot_gridview)
        MosaicGridView hot_gridview;
        @Bind(R.id.select_good)
        TextView tv_good;
        @Bind(R.id.select_cyclopedia)
        TextView tv_cyclopedia;
        @Bind(R.id.select_posts)
        TextView tv_posts;
        @Bind(R.id.search_header_type)
        LinearLayout ll_type;
        @Bind(R.id.tv_hot)
        TextView tv_hot;


        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.select_good)
        void goodClick() {
            tv_good.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.good_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.white));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            cat_id = 7;
            presenter.loadHot(cat_id);
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索商品");
            tv_hot.setVisibility(View.VISIBLE);
        }

        @OnClick(R.id.select_cyclopedia)
        void CycloClick() {
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.good_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.white));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            cat_id = 6;
            presenter.loadHot(cat_id);
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索百科");
            tv_hot.setVisibility(View.VISIBLE);
        }

        @OnClick(R.id.select_posts)
        void postsClick() {
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.white));
            cat_id = 8;
            hotlist.clear();
            hotadapter.notifyDataSetChanged();
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索帖子");
            tv_hot.setVisibility(View.GONE);
        }

        @OnClick(R.id.search_history_clear)
        void clearClick() {
            historylist.clear();
            hisadapter.notifyDataSetChanged();
            DaoUtils.deleteHistory(cat_id, SearchActivity.this);
        }
    }

}
