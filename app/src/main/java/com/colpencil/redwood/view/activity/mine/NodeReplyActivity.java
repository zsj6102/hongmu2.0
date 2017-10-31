package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;

import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.FirstComment;
import com.colpencil.redwood.bean.NodeReplyItem;

import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.NodeReplyPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.NodeReplyAdapter;
import com.colpencil.redwood.view.impl.INodeReply;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

import static com.colpencil.redwood.holder.HolderFactory.map;


/**
 * 评价
 */

@ActivityFragmentInject(contentViewId = R.layout.node_reply_layout)
public class NodeReplyActivity extends ColpencilActivity implements INodeReply {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.right_listview)
    ListView rightListview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.search_header_hint)
    EditText searchHeaderHint;
    @Bind(R.id.comment_count)
    TextView commentCount;
    private int goods_id;
    private NodeReplyPresenter presenter;
    private NodeReplyAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private List<NodeReplyItem> mdata = new ArrayList<>();
    private int pos;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    @Override
    protected void initViews(View view) {
        goods_id = getIntent().getExtras().getInt("goodsid");
        Map<String, String> nummap = new HashMap<>();
        nummap.put("h_id", goods_id + "");
        nummap.put("type", "1");
        presenter.loadCommentsNum(nummap);
        tvMainTitle.setText("评价");
        loadPageOne(1);
        adapter = new NodeReplyAdapter(this, mdata, R.layout.node_reply_item);
        initAdapter();
        rightListview.setAdapter(adapter);
      initBus();
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                loadPageOne(1);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    loadPageOne(pageNo);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }

    private void initBus(){
        observable = RxBus.get().register("rxBusMsg",RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(rxBusMsg.getType() == 123){
                    loadPageOne(1);
                }
            }
        };
        observable.subscribe(subscriber);
    }
    private void initAdapter() {
        adapter.setListener(new NodeReplyAdapter.MyListener() {
            @Override
            public void addLike(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("reply_id", mdata.get(position).getRe_id() + "");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    map.put("biaoshi", "1");
                    presenter.getLikeResult(map);
                } else {
                    showDialog();
                }
            }

            @Override
            public void commentClick(int position) {
                Intent intent = new Intent(NodeReplyActivity.this, ReplyDetail.class);
                //                intent.putExtra("re_id",mdata.get(position).getRe_id());
                intent.putExtra("data", mdata.get(position));
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadPageOne(1);
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                intent();
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void intent() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(StringConfig.REQUEST_CODE, 100);
        startActivityForResult(intent, Constants.REQUEST_LOGIN);
    }

    private void loadPageOne(int pageNo) {
        Map<String, String> map = new HashMap<>();
        map.put("h_id", goods_id + "");
        map.put("type", 1 + "");
        map.put("page", pageNo + "");
        map.put("pageSize", pageSize + "");
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        presenter.getNodeReply(pageNo, map);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new NodeReplyPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(ResultInfo<List<NodeReplyItem>> info) {
        isLoadMore(info.getData());
        mdata.clear();
        mdata.addAll(info.getData());
        pageNo = 1;
        rightListview.removeAllViewsInLayout();
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @Override
    public void loadNums(FirstComment result) {
        if (result.getCode() == 0) {
            commentCount.setText(result.getData() + "");
        }
    }

    @Override
    public void addComment(AddResult result) {
        hideLoading();
        ToastTools.showShort(this, result.getMessage());
        if (result.getCode() == 0) {
            loadPageOne(1);
        }
    }

    @Override
    public void loadMore(ResultInfo<List<NodeReplyItem>> info) {
        isLoadMore(info.getData());
        mdata.addAll(info.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String message) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void isLoadMore(List<NodeReplyItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @OnClick(R.id.tv_sure)
    void commit() {
        if(SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)){
            if (searchHeaderHint.getText().toString().trim() != null ) {
                showLoading("正在提交中...");
                Map<String, String> map = new HashMap<String, String>();
                map.put("h_id", goods_id + "");
                map.put("type", "1");
                map.put("content", searchHeaderHint.getText().toString());
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                presenter.getAddCommentResult(map);
            } else {
                ToastTools.showShort(NodeReplyActivity.this, "请输入评论内容");
            }
        }else{
            showDialog();
        }


    }

    @Override
    public void addLike(ResultInfo<String> resultInfo) {
        if (resultInfo.getCode() == 0) {
            if (mdata.get(pos).getIsfocus() == 0) {
                mdata.get(pos).setIsfocus(1);
                mdata.get(pos).setRe_like_count(mdata.get(pos).getRe_like_count() + 1);
                adapter.notifyDataSetChanged();
            } else {
                mdata.get(pos).setIsfocus(0);
                mdata.get(pos).setRe_like_count(mdata.get(pos).getRe_like_count() - 1);
                adapter.notifyDataSetChanged();
            }
        }
        ToastTools.showShort(this, resultInfo.getMessage());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg",observable);
    }
}
