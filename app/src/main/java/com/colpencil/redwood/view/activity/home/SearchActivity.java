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

import static android.view.View.Z;
import static com.colpencil.redwood.R.id.select_supai;
import static com.unionpay.mobile.android.global.a.r;

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
    private String from;
    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        from = getIntent().getStringExtra("from");

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
        if(from.equals("AllAuction")){
            holder.tv_supai.performClick();
        }else if(from.equals("Brand")){
            holder.tv_good.performClick();
        }else if(from.equals("AllAuctionCard") || from.equals("MRT")){
            holder.tv_store.performClick();
        }else if(from.equals("Zc")){
            holder.tv_zc.performClick();
        }
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
        } else if(cat_id == 8){
            intent.setClass(this, PostsResultActivity.class);
        }else if(cat_id == 9){
            intent.setClass(this, SearchStoreActivity.class);
        }else  if(cat_id == 10){
            intent.setClass(this, ZcSearchActivity.class);
        }else if(cat_id == 11){
            intent.setClass(this,WeekSearchActivity.class);
        }else if(cat_id == 12){
            intent.setClass(this,SupaiSearchActivity.class);
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
        @Bind(R.id.select_store)
        TextView tv_store;
        @Bind(R.id.select_zc)
        TextView tv_zc;
        @Bind(R.id.select_week)
        TextView tv_week;
        @Bind(select_supai)
        TextView tv_supai;

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
            tv_store.setBackgroundResource(R.drawable.good_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_good.setTextColor(getResources().getColor(R.color.white));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_zc.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setBackgroundResource(R.drawable.good_circle_shape);
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
            tv_store.setBackgroundResource(R.drawable.good_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.white));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_zc.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setBackgroundResource(R.drawable.good_circle_shape);
            cat_id = 6;
            presenter.loadHot(cat_id);
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索百科");
            tv_hot.setVisibility(View.VISIBLE);
        }

        @OnClick(R.id.select_week)
        void weekClick(){
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.white));
            tv_posts.setBackgroundResource(R.drawable.good_circle_shape);
            tv_store.setBackgroundResource(R.drawable.good_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.good_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_zc.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setBackgroundResource(R.drawable.good_circle_shape);
            cat_id = 11;
            hotlist.clear();
            hotadapter.notifyDataSetChanged();
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索周拍");
            tv_hot.setVisibility(View.GONE);
        }
        @OnClick(R.id.select_posts)
        void postsClick() {
            tv_week.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_store.setBackgroundResource(R.drawable.good_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.good_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_zc.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.white));
            tv_supai.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setBackgroundResource(R.drawable.good_circle_shape);
            cat_id = 8;
            hotlist.clear();
            hotadapter.notifyDataSetChanged();
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索帖子");
            tv_hot.setVisibility(View.GONE);
        }

        @OnClick(R.id.select_store)
        void storeClick(){
            tv_week.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.good_circle_shape);
            tv_store.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.good_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.white));
            tv_zc.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setBackgroundResource(R.drawable.good_circle_shape);
            cat_id = 9;
            hotlist.clear();
            hotadapter.notifyDataSetChanged();
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索商家");
            tv_hot.setVisibility(View.GONE);
        }

        @OnClick(R.id.select_zc)
        void zcClick(){
            tv_week.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.good_circle_shape);
            tv_store.setBackgroundResource(R.drawable.good_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_zc.setTextColor(getResources().getColor(R.color.white));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_supai.setBackgroundResource(R.drawable.good_circle_shape);
            cat_id = 10;
            hotlist.clear();
            hotadapter.notifyDataSetChanged();
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索专场");
            tv_hot.setVisibility(View.GONE);
        }
        @OnClick(select_supai)
        void supaiClick(){
            tv_supai.setTextColor(getResources().getColor(R.color.white));
            tv_supai.setBackgroundResource(R.drawable.cyclopedia_circle_shape);
            tv_week.setBackgroundResource(R.drawable.good_circle_shape);
            tv_week.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_good.setBackgroundResource(R.drawable.good_circle_shape);
            tv_cyclopedia.setBackgroundResource(R.drawable.good_circle_shape);
            tv_posts.setBackgroundResource(R.drawable.good_circle_shape);
            tv_store.setBackgroundResource(R.drawable.good_circle_shape);
            tv_zc.setBackgroundResource(R.drawable.good_circle_shape);
            tv_good.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_cyclopedia.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_store.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_zc.setTextColor(getResources().getColor(R.color.text_color_thirst));
            tv_posts.setTextColor(getResources().getColor(R.color.text_color_thirst));
            cat_id = 12;
            hotlist.clear();
            hotadapter.notifyDataSetChanged();
            presenter.loadHistory(cat_id, SearchActivity.this);
            edit.setHint("搜索速拍");
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
