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
import com.colpencil.redwood.present.NodeReplyPresenter;
import com.colpencil.redwood.view.adapters.NodeReplyAdapter;
import com.colpencil.redwood.view.impl.INodeReply;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
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
    Map<String, String> map = new HashMap<>();

    @Override
    protected void initViews(View view) {
        goods_id = getIntent().getExtras().getInt("goodsid");
        Map<String,String> nummap = new HashMap<>();
        nummap.put("h_id",goods_id+"");
        nummap.put("type","1");
        presenter.loadCommentsNum(nummap);
        tvMainTitle.setText("评价");
        map.put("h_id", goods_id + "");
        map.put("type", 1 + "");
        map.put("page", pageNo + "");
        map.put("pageSize", pageSize + "");
        presenter.getNodeReply(1, map);
        adapter = new NodeReplyAdapter(this, mdata, R.layout.node_reply_item);
        initAdapter();
        rightListview.setAdapter(adapter);

        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                map.put("page", pageNo + "");
                presenter.getNodeReply(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    map.put("page", pageNo + "");
                    presenter.getNodeReply(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }
    private void initAdapter(){
        adapter.setListener(new NodeReplyAdapter.MyListener() {
            @Override
            public void commentClick(int position) {
                Intent intent = new Intent(NodeReplyActivity.this,ReplyDetail.class);
//                intent.putExtra("re_id",mdata.get(position).getRe_id());
                intent.putExtra("data",mdata.get(position));
                startActivity(intent);
            }
        });
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
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadNums(FirstComment result) {
        if(result.getCode() == 0){
            commentCount.setText(result.getData()+"");
        }
    }

    @Override
    public void addComment(AddResult result) {
        hideLoading();
        ToastTools.showShort(this,result.getMessage());
        if(result.getCode() == 0){
            pageNo = 1;
            map.put("page", pageNo + "");
            presenter.getNodeReply(pageNo, map);
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
    void commit(){
        if(searchHeaderHint.getText().toString().trim()!=null){
            showLoading("正在提交中...");
            Map<String,String> map = new HashMap<String, String>();
            map.put("h_id", goods_id+"");
            map.put("type","1");
            map.put("content",searchHeaderHint.getText().toString());
            map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
            map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            presenter.getAddCommentResult(map);
        }else{
            ToastTools.showShort(NodeReplyActivity.this,"请输入评论内容");
        }

    }
}
