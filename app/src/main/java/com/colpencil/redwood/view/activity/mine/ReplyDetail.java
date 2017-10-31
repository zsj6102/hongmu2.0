package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.bean.ReplyToItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.ReplyDetailPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.ReplytToAdapter;
import com.colpencil.redwood.view.impl.IReplyDetailView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评价详情
 */

@ActivityFragmentInject(contentViewId = R.layout.activity_reply_detail)
public class ReplyDetail extends ColpencilActivity implements IReplyDetailView {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.right_listview)
    ListView rightListview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.iv_meHead)
    SelectableRoundedImageView ivMeHead;
    @Bind(R.id.tv_title1)
    TextView tvTitle1;
    @Bind(R.id.iv_level)
    ImageView ivLevel;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.search_header_hint)
    EditText searchHeaderHint;
    private ReplyDetailPresenter presenter;

    private int pageNo = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    NodeReplyItem item;
    private ReplytToAdapter adapter;
    private List<ReplyToItem> mdata = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    private int tomember = 0;//回复楼主
    private int pos;
    private int type;
    @Override
    protected void initViews(View view) {
        item = (NodeReplyItem) getIntent().getSerializableExtra("data");
        type = getIntent().getExtras().getInt("type");
        tvMainTitle.setText("详情");
        map.put("re_id", item.getRe_id() + "");
        map.put("type", type + "");
        map.put("page", pageNo + "");
        map.put("pageSize", pageSize + "");
        presenter.getDetail(1, map);
        adapter =  new ReplytToAdapter(this,mdata,R.layout.repley_to_item);
        initAdapter();
        rightListview.setAdapter(adapter);
        ImageLoaderUtils.loadImage(this,item.getRe_face(),ivMeHead);
        tvTitle1.setText(item.getRe_store_name());
        ImageLoaderUtils.loadImage(this,item.getRe_member_photo(),ivLevel);
        content.setText(item.getRe_content());
        time.setText(item.getRe_create_time());
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                map.put("page", pageNo + "");
                presenter.getDetail(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    map.put("page", pageNo + "");
                    presenter.getDetail(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }

    private void initAdapter(){
        adapter.setListener(new ReplytToAdapter.MyListener() {
            @Override
            public void commentClick(int position) {
                pos = position;
                tomember = 1;
                searchHeaderHint.setHint("回复 "+ mdata.get(position).getStore_name()+":");
            }
        });
    }
    @OnClick(R.id.iv_back)
    void back(){
        finish();
    }
    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new ReplyDetailPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void loadMore(ResultInfo<List<ReplyToItem>> info) {
        hideLoading();

        isLoadMore(info.getData());
        mdata.addAll(info.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(ResultInfo<List<ReplyToItem>> info) {
        hideLoading();
        isLoadMore(info.getData());
        mdata.clear();
        mdata.addAll(info.getData());
        rightListview.removeAllViewsInLayout();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void addReply(AddResult result) {
        hideLoading();
        if(result.getCode() == 0){
            pageNo = 1;
            map.put("page", pageNo + "");
            presenter.getDetail(pageNo, map);
        }
        ToastTools.showShort(this,result.getMessage());
        RxBusMsg msg = new RxBusMsg();
        msg.setType(123);
        RxBus.get().post("rxBusMsg",msg);
    }

    private void isLoadMore(List<ReplyToItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tv_sure)
    void commit(){
        if(SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)){
            if(searchHeaderHint.getText().toString().trim()!=null){

                showLoading("正在提交中...");
                Map<String,String> map = new HashMap<String, String>();
                map.put("re_id", item.getRe_id()+"");
                map.put("type",""+type);
                map.put("content",searchHeaderHint.getText().toString());
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));

                if(tomember == 0){
                    //                map.put("to_member",item.getRe_member_id()+"");   to_member可以为空 空标识对楼主
                    String hint;
                    hint=searchHeaderHint.getHint().toString();
                    searchHeaderHint.setText("");
                    searchHeaderHint.setHint(hint);
                    presenter.getAddReply(map);
                }else{
                    tomember = 0;
                    searchHeaderHint.setText("");
                    searchHeaderHint.setHint("点击评论一下");
                    map.put("to_member",mdata.get(pos).getMember_id()+"");
                    presenter.getAddReply(map);
                }

            }else{
                ToastTools.showShort(ReplyDetail.this,"请输入评论内容");
            }
        }else{
            showDialog();
        }
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
}
