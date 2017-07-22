package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.present.mine.ApplyStatusPresenter;
import com.colpencil.redwood.view.impl.IBuisnessView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;



/**
 * @author 陈宝
 * @Description:商家合作
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_business)
public class BusinessActivity extends ColpencilActivity implements View.OnClickListener, IBuisnessView {
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.ll_person)
    LinearLayout ll_person;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.ll_brand)
    LinearLayout ll_brand;
    @Bind(R.id.ll_famous)
    LinearLayout ll_famous;
    @Bind(R.id.layout_status)
    RelativeLayout layoutStatus;
    @Bind(R.id.layout_apply)
    LinearLayout layoutApply;
    @Bind(R.id.tv_corperate)
    TextView tvCorperate;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.tv_status_content)
    TextView tvStatusContent;
    @Bind(R.id.tv_resion)
    TextView tvResion;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.layout_is_store)
    LinearLayout layoutIsStore;
    @Bind(R.id.layout_store1)
    LinearLayout layoutStore1;
    @Bind(R.id.layout_store2)
    LinearLayout layoutStore2;
    @Bind(R.id.layout_store3)
    LinearLayout layoutStore3;
    @Bind(R.id.layout_store4)
    LinearLayout layoutStore4;
    @Bind(R.id.layout_store5)
    LinearLayout layoutStore5;
    @Bind(R.id.layout_store6)
    LinearLayout layoutStore6;
    @Bind(R.id.layout_store7)
    LinearLayout layoutStore7;
    @Bind(R.id.layout_store8)
    LinearLayout layoutStore8;

    private Observable<RefreshMsg> observable;
    private ApplyStatusPresenter presenter;
    private Subscriber subscriber;
    private int store_id;
    private String storename;
    private int store_type;
    @Override
    protected void initViews(View view) {
        tv_title.setText("商家合作");
        ll_person.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_brand.setOnClickListener(this);
        ll_famous.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        layoutStore1.setOnClickListener(this);
        layoutStore2.setOnClickListener(this);
        layoutStore3.setOnClickListener(this);
        layoutStore4.setOnClickListener(this);
        layoutStore5.setOnClickListener(this);
        layoutStore6.setOnClickListener(this);
        layoutStore7.setOnClickListener(this);
        layoutStore8.setOnClickListener(this);
        tvCorperate.setText("<" + "合作热线：13888999899" + ">");
        HashMap<String, String> params = new HashMap<>();
        //去获取申请状态
        params.put("member_id", SharedPreferencesUtil.getInstance(this).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(this).getString("token"));
        presenter.getApplyStatus(params);
        showLoading("");
        initBus();

    }

    @Override
    public ColpencilPresenter getPresenter() {

        presenter = new ApplyStatusPresenter();
        return presenter;
    }

    //如果还在后台上传中就跳到这个页面,状态改变通知
    private void initBus() {
        observable = RxBus.get().register("refreshmsg", RefreshMsg.class);
        subscriber = new Subscriber<RefreshMsg>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg msg) {
                if (msg.getType() == 22 || msg.getType() == 23 || msg.getType() == 21) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("member_id", SharedPreferencesUtil.getInstance(BusinessActivity.this).getInt("member_id") + "");
                    params.put("token", SharedPreferencesUtil.getInstance(BusinessActivity.this).getString("token"));
                    presenter.getApplyStatus(params);
                    showLoading("");
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.iv_back:
                RefreshMsg msg = new RefreshMsg();
                msg.setType(0);//个人 二期
                RxBus.get().post("refreshmsg", msg);
                finish();
                break;
            case R.id.ll_person:
                intent = new Intent(this, PersonApplyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_brand:
                intent = new Intent(this, BrandApplyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ll_famous:
                intent = new Intent(this, FamousApplyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.layout_store1:
                if(store_type == 1){
                    intent = new Intent(this,PublishStoreActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(this,PublishListActivity.class);
                    intent.putExtra("type",store_type+"");
                    intent.putExtra("name", storename);
                    intent.putExtra("id",store_id+"");
                    startActivity(intent);
                }

            case R.id.tv_commit:
                setApplyVisible();
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getStatusError(String message) {
        Toast.makeText(this, "网络出错", Toast.LENGTH_SHORT).show();
        setApplyVisible();
    }

    @Override
    public void getStatusSucess(ApplyStatusReturn applyStatusReturn) {
        hideLoading();
        show(applyStatusReturn);
    }

    private void show(ApplyStatusReturn applyStatusReturn) {
        if (applyStatusReturn.getCode() == 0) {                     //成功
            if (applyStatusReturn.getData().getDisabled() == -2) {         //未成为商家
                setApplyVisible();
            } else if (applyStatusReturn.getData().getDisabled() == 0) {      //审核中
                setApplyGone();
                tvStatus.setText("审核中");
                tvStatusContent.setText("您的申请已提交，请耐心等待！");
                tvResion.setVisibility(View.GONE);
                tvCommit.setVisibility(View.GONE);
                tv_title.setText("我的店铺");
            } else if (applyStatusReturn.getData().getDisabled() == -1) {      //未通过审核
                setApplyGone();
                tvStatus.setText("审核未通过");
                tvStatusContent.setText("很抱歉，您的认证消息未审核通过！");
                if (applyStatusReturn.getData().getReason() != null && !applyStatusReturn.getData().getReason().trim().equals("")) {
                    tvResion.setText("原因：" + applyStatusReturn.getData().getReason());
                    tvResion.setVisibility(View.VISIBLE);
                }
                tv_title.setText("我的店铺");
                tvCommit.setVisibility(View.VISIBLE);
            } else if (applyStatusReturn.getData().getDisabled() == 2) {           //冻结
                setApplyGone();
                tvStatus.setText("冻结中");
                tvStatusContent.setText("您的店铺已被冻结，请联系客服！");
                tvResion.setVisibility(View.GONE);
                tvCommit.setVisibility(View.GONE);
                tv_title.setText("我的店铺");
            } else if (applyStatusReturn.getData().getDisabled() == 1) {
                tv_title.setText("我的店铺");
                layoutIsStore.setVisibility(View.VISIBLE);//通过审核，并在运营中
                layoutApply.setVisibility(View.GONE);
                layoutStatus.setVisibility(View.GONE);
                store_type = applyStatusReturn.getData().getStore_type();
                store_id = applyStatusReturn.getData().getStore_id();
                storename = applyStatusReturn.getData().getStore_name();
            }
        } else if (applyStatusReturn.getCode() == 1) {              //失败
            Toast.makeText(this, "网络出错", Toast.LENGTH_SHORT).show();
            layoutApply.setVisibility(View.GONE);
            layoutStatus.setVisibility(View.GONE);
        } else if (applyStatusReturn.getCode() == 2) {               //未登录
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
            layoutApply.setVisibility(View.GONE);
            layoutStatus.setVisibility(View.GONE);
        }
    }

    private void setApplyVisible() {
        layoutApply.setVisibility(View.VISIBLE);
        layoutStatus.setVisibility(View.GONE);
    }

    private void setApplyGone() {
        layoutApply.setVisibility(View.GONE);
        layoutStatus.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshmsg", observable);
    }
}
