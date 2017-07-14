package com.colpencil.redwood.view.activity.classification;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.SortList;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.CommoditySearchPresenter;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.adapters.CommoditySearchAdapter;
import com.colpencil.redwood.view.adapters.ScreenSearchAdapter;
import com.colpencil.redwood.view.impl.ICommoditySearchView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.AnimatedExpandableListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * 描述：商品搜索
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/12 16 03
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_commoditysearch
)
public class CommoditySearchActivity extends ColpencilActivity implements ICommoditySearchView, BGARefreshLayoutDelegate {

    @Bind(R.id.bga_commoditySearch)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview_CommoditySearch)
    BaseGridView gridView;
    @Bind(R.id.tv_defaultSearch)
    TextView defaultTv;
    @Bind(R.id.tv_sellSearch)
    TextView sellTv;
    @Bind(R.id.tv_priceSearch)
    TextView priceTv;
    @Bind(R.id.iv_priceSearch)
    ImageView priceIv;
    @Bind(R.id.tv_screenSearch)
    TextView screenTv;
    @Bind(R.id.iv_screenSearch)
    ImageView screenIv;
    @Bind(R.id.et_search)
    EditText et_search;
    //抽屉设置
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.menu_layout_left)
    RelativeLayout mMenu_layout_left;//左边抽屉
    private CommoditySearchAdapter mAdapter;
    private CommoditySearchPresenter presenter;
    /**
     * 分页请求页码
     */
    private int pageNo = 1;
    /**
     * 每页信息条数
     */
    private int pageSize = 10;
    /**
     * 是否可进行上拉加载操作
     */
    private boolean flag;

    private List<HomeGoodInfo> datas = new ArrayList<>();
    /**
     * 搜索方式标识
     */
    private int searchType;
    private List<SortList> father = new ArrayList<>();
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private ScreenSearchAdapter screenSearchAdapter;
    /**
     * 二级分类id
     */
    private int child_cat_id;
    private int typeFlag = 1;//服务请求标识
    private String msgSearch;
    private RightViewHolder holder;
    private String keyword = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                screenSearchAdapter = new ScreenSearchAdapter(father, CommoditySearchActivity.this);
                holder.listView.setAdapter(screenSearchAdapter);
                setexpandListen();
                //将所有项设置成默认展开
                for (int i = 0; i < father.size(); i++) {
                    holder.listView.expandGroup(i);
                }
            } else if (msg.what == 2) {
                mAdapter.notifyDataSetChanged();
                //停止刷新
                refreshLayout.endRefreshing(0);
                refreshLayout.endLoadingMore();
            }
        }
    };

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        child_cat_id = getIntent().getIntExtra("child_cat_id", 0);
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        View rigthView = View.inflate(this, R.layout.activity_screensearch, null);
        holder = new RightViewHolder(rigthView);
        initRigthLayput();
        mMenu_layout_left.addView(rigthView);
        initData();
        et_search.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pageNo = 1;
                keyword = et_search.getText().toString();
                typeFlag = -1;
                loadData();
                return false;
            }
        });
    }

    private void initRigthLayput() {
        holder.listView.setCacheColorHint(Color.TRANSPARENT);
        holder.listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        holder.listView.setGroupIndicator(null);
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 0) {
                    father.get(msg.getPositonGroupMsg()).setRightTitleName(father.get(msg.getPositonGroupMsg()).getSort_list().get(msg.getPositonChildMsg()).getSort_name());
                    for (int i = 0; i < father.get(msg.getPositonGroupMsg()).getSort_list().size(); i++) {
                        if (i == msg.getPositonChildMsg()) {
                            if (father.get(msg.getPositonGroupMsg()).getSort_list().get(msg.getPositonChildMsg()).isChooseState()) {
                                father.get(msg.getPositonGroupMsg()).getSort_list().get(msg.getPositonChildMsg()).setChooseState(false);
                            } else {
                                father.get(msg.getPositonGroupMsg()).getSort_list().get(msg.getPositonChildMsg()).setChooseState(true);
                            }
                        } else {
                            father.get(msg.getPositonGroupMsg()).getSort_list().get(i).setChooseState(false);
                        }
                    }
                    screenSearchAdapter.notifyDataSetChanged();
                }
            }
        };
        observable.subscribe(subscriber);
    }

    /**
     * 设置展开监听
     */
    private void setexpandListen() {
        holder.listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //更换箭头符号
                ScreenSearchAdapter.GroupViewHolder viewHolder = (ScreenSearchAdapter.GroupViewHolder) v.getTag();
                if (holder.listView.isGroupExpanded(groupPosition)) {
                    viewHolder.iv_expand.setImageResource(R.mipmap.screensearch_close);
                } else {
                    viewHolder.iv_expand.setImageResource(R.mipmap.screensearch_open);
                }
                return false;
            }
        });
    }

    /**
     * 数据初始化
     */
    private void initData() {
        mAdapter = new CommoditySearchAdapter(this, datas, R.layout.item_commoditysearch);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommoditySearchActivity.this, GoodDetailActivity.class);
                intent.putExtra("goodsId", datas.get(position).getGoodsid());
                startActivity(intent);
            }
        });
        presenter.sortData(child_cat_id);//请求筛选数据
        loadData();//查询商品
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CommoditySearchPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    /**
     * 数据加载
     */
    private void loadData() {
        showLoading(Constants.progressName);
        if (typeFlag != -1) {
            presenter.loadGoodInforByType(child_cat_id, typeFlag, pageNo, pageSize);//默认排序方式
        } else {
            presenter.findGoodsByKeyWord(child_cat_id, keyword, pageNo, pageSize);  //处理搜索
        }
        pageNo++;
    }

    /**
     * 重置数据
     */
    private void reSetData() {
        pageNo = 1;
        loadData();
    }

    @Override
    public void refresh(List<HomeGoodInfo> data) {
        datas.clear();
        datas.addAll(data);//添加最新数据
        handler.sendEmptyMessage(2);
        //判断是否可以进行上拉加载更多操作
        isLoadMore(data.size());
    }

    @Override
    public void loadMore(List<HomeGoodInfo> data) {
        datas.addAll(data);//添加最新数据
        mAdapter.notifyDataSetChanged();
        //停止加载
        refreshLayout.endLoadingMore();
        isLoadMore(data.size());
    }

    @Override
    public void loadSort(List<SortList> data) {
        father.addAll(data);//添加父类数据
        handler.sendEmptyMessage(1);
    }

    @Override
    public void fail(String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        pageNo = 1;
        loadData();
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (flag == true) {
            loadData();
        }
        return false;
    }

    /**
     * 设置是否可进行上拉加载操作
     */
    private void isLoadMore(int size) {
        if (size < pageSize) {
            flag = false;
        } else {
            flag = true;
        }
        hideLoading();
    }

    /**
     * 设置字体颜色
     */
    private void setTextColor() {
        defaultTv.setTextColor(getResources().getColor(R.color.text_color_second));
        sellTv.setTextColor(getResources().getColor(R.color.text_color_second));
        priceTv.setTextColor(getResources().getColor(R.color.text_color_second));
        screenTv.setTextColor(getResources().getColor(R.color.text_color_second));
    }

    /**
     * 点击默认
     */
    @OnClick(R.id.ll_defaultSearch)
    void defaultClick() {
        priceIv.setImageResource(R.mipmap.price_default);
        optionChoose(1, defaultTv);
    }

    /**
     * 点击销量优先
     */
    @OnClick(R.id.ll_sellSearch)
    void sellClick() {
        priceIv.setImageResource(R.mipmap.price_default);
        optionChoose(2, sellTv);
    }

    /**
     * 点击价格
     */
    @OnClick(R.id.ll_priceSearch)
    void priceClick() {
        if (typeFlag != 3 && typeFlag != 4) {
            priceIv.setImageResource(R.mipmap.price_top);
            optionChoose(3, priceTv);
            priceTv.setTextColor(getResources().getColor(R.color.main_red));
        } else {
            if (typeFlag == 3) {
                typeFlag = 4;
                priceIv.setImageResource(R.mipmap.price_down);
                optionChoose(4, priceTv);
                priceTv.setTextColor(getResources().getColor(R.color.main_red));
            } else {
                typeFlag = 1;
                priceIv.setImageResource(R.mipmap.price_default);
                optionChoose(1, priceTv);
                priceTv.setTextColor(getResources().getColor(R.color.text_color_second));
            }
        }
    }

    /**
     * 点击筛选
     */
    @OnClick(R.id.ll_screenSearch)
    void screenClick() {
        setTextColor();
        screenIv.setImageResource(R.mipmap.screen_choose);
        screenTv.setTextColor(getResources().getColor(R.color.main_red));
        priceIv.setImageResource(R.mipmap.price_default);
        drawer_layout.openDrawer(mMenu_layout_left);
    }

    /**
     * 返回
     */
    @OnClick(R.id.iv_commoditySearchBack)
    void backClick() {
        finish();
    }

    @OnClick(R.id.totop_iv)
    void top() {
        gridView.setSelection(0);
    }

    private void optionChoose(int type, TextView tv) {
        if (searchType != type) {
            searchType = type;
            setTextColor();
            tv.setTextColor(getResources().getColor(R.color.main_red));
            screenIv.setImageResource(R.mipmap.screen);
            typeFlag = type;
            reSetData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        holder.unbind();
    }

    class RightViewHolder {

        @Bind(R.id.price_low)
        EditText et_low;
        @Bind(R.id.price_high)
        EditText et_high;
        @Bind(R.id.expand_screenSearch)
        AnimatedExpandableListView listView;

        public RightViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unbind() {
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.tv_screensearch_sumbit)
        void submitClick() {
            showLoading(Constants.progressName);
            drawer_layout.closeDrawer(mMenu_layout_left);
            msgSearch = "";
            for (int i = 0; i < father.size(); i++) {
                for (int j = 0; j < father.get(i).getSort_list().size(); j++) {
                    if (father.get(i).getSort_list().get(j).isChooseState() == true) {
                        msgSearch = msgSearch + father.get(i).getSort_list().get(j).getSort_id() + ",";
                    }
                }
            }
            String low = et_low.getText().toString();
            String high = et_high.getText().toString();
            String price_range = "";
            if (!TextUtils.isEmpty(low) || !TextUtils.isEmpty(high)) {
                price_range = low + "_" + high;
            }
            pageNo = 1;
            presenter.loadGoodInforByType(child_cat_id,
                    msgSearch,
                    price_range,
                    pageNo, pageSize);
        }

        @OnClick(R.id.tv_screensearch_reSet)
        void resetClick() {
            for (int i = 0; i < father.size(); i++) {
                for (int j = 0; j < father.get(i).getSort_list().size(); j++) {
                    if (father.get(i).getSort_list().get(j).isChooseState() == true) {
                        father.get(i).getSort_list().get(j).setChooseState(false);
                        father.get(i).setRightTitleName("");
                    }
                }
            }
            et_low.setText("");
            et_high.setText("");
            screenSearchAdapter.notifyDataSetChanged();
        }

    }
}
